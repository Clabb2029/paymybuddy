package com.pay_my_buddy.paymybuddy.controller;

import com.pay_my_buddy.paymybuddy.model.User;
import com.pay_my_buddy.paymybuddy.exception.EmailAlreadyExistingException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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
        User loggedUser = userService.getAuthenticatedUser();
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
        User loggedUser = userService.getAuthenticatedUser();
        model.addAttribute("user", loggedUser);
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
    public String registration(@Valid @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        try {
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("created", "Your account has been successfully created");
            return "redirect:/login";
        } catch (IllegalArgumentException | EmailAlreadyExistingException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }

    @PostMapping("/edit-user")
    public String editUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes, Model model) {
        if(user.getFirstname().isEmpty() || user.getLastname().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please fill in all fields");
        } else {
            User userSaved = userService.saveUser(user);
            model.addAttribute("userSaved", userSaved);
            redirectAttributes.addFlashAttribute("toastText", "Your name has been successfully saved");
        }
        return "redirect:/profile";
    }

}
