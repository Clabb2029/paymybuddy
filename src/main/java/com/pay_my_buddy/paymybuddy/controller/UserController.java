package com.pay_my_buddy.paymybuddy.controller;

import com.pay_my_buddy.paymybuddy.DTO.User;
import com.pay_my_buddy.paymybuddy.exception.EmailAlreadyExistingException;
import com.pay_my_buddy.paymybuddy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        User loggedUser = userService.getAuthenticatedUser();
        model.addAttribute("user", loggedUser);
        return "home";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        System.out.println(authentication);
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
}
