package com.pay_my_buddy.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

    @GetMapping("/contact")
    public String showContactPage(Model model) {
        model.addAttribute("activePage", "contact");
        model.addAttribute("titlePage", "Transfer");
        return "contact";
    }
}
