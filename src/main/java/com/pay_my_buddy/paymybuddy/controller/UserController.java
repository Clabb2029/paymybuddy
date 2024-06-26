package com.pay_my_buddy.paymybuddy.controller;

import com.pay_my_buddy.paymybuddy.DTO.UserDTO;
import com.pay_my_buddy.paymybuddy.model.User;
import com.pay_my_buddy.paymybuddy.exception.EmailAlreadyExistingException;
import com.pay_my_buddy.paymybuddy.model.viewModel.UserProfileViewForm;
import com.pay_my_buddy.paymybuddy.service.RelationService;
import com.pay_my_buddy.paymybuddy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RelationService relationService;

    private AuthenticationManager authenticationManager;

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        if (isAuthenticated()) {
            return "redirect:/home";
        }
        model.addAttribute("user", new User());
        model.addAttribute("titlePage", "Log In");
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("titlePage", "Register");
        return "register";
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        UserDTO loggedUser = userService.getAuthenticatedUser();
        Optional<User> lastRelation = relationService.getLastAddedRelationOfUser(loggedUser.getId());
        lastRelation.ifPresent(user -> model.addAttribute("relation", user));
        model.addAttribute("user", loggedUser);
        model.addAttribute("searchedUser", new User());
        model.addAttribute("userToAdd", new User());
        model.addAttribute("activePage", "home");
        model.addAttribute("titlePage", "Home");
        return "home";
    }

    @GetMapping("/profile")
    public String showEditUserPage(Model model) {
        UserDTO loggedUser = userService.getAuthenticatedUser();
        UserProfileViewForm user = new UserProfileViewForm(loggedUser.getLastname(), loggedUser.getFirstname(), loggedUser.getEmail());
        model.addAttribute("user", user);
        model.addAttribute("userSaved", null);
        model.addAttribute("activePage", "profile");
        model.addAttribute("titlePage", "Profile");
        return "profile";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @ModelAttribute("user") User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User successfully logged in!", HttpStatus.OK);
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", user);
            return "register";
        }
        try {
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("toastText", "Your account has been successfully created. Please log in to access your account.");
            return "redirect:/login";
        } catch (IllegalArgumentException | EmailAlreadyExistingException e) {
            redirectAttributes.addFlashAttribute("toastText", "This email is already used. Please try again with another email or log in with this email.");
            return "redirect:/register";
        }
    }

    @PostMapping("/edit-user")
    public String editUser(@Valid @ModelAttribute("user") UserProfileViewForm user, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", user);
            return "profile";
        }
        User userSaved = userService.saveUser(user);
        model.addAttribute("userSaved", userSaved);
        redirectAttributes.addFlashAttribute("toastText", "Your name has been successfully saved");
        return "redirect:/profile";
    }

}
