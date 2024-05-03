package com.pay_my_buddy.paymybuddy.controller;

import com.pay_my_buddy.paymybuddy.model.Relation;
import com.pay_my_buddy.paymybuddy.model.User;
import com.pay_my_buddy.paymybuddy.service.RelationService;
import com.pay_my_buddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class RelationController {

    @Autowired
    private RelationService relationService;

    @Autowired
    private UserService userService;

    @PostMapping("relation/search-buddy")
    public String searchBuddy(@ModelAttribute("user") User searchedUser, RedirectAttributes redirectAttributes) {
        try {
            Optional<User> fetchedUser = userService.getUserByEmailOtherThanCurrentUser(userService.getAuthenticatedUser().getEmail(), searchedUser.getEmail());
            redirectAttributes.addFlashAttribute("searchResult", true);
            if(fetchedUser.isPresent()) {
                Optional<Relation> relation = relationService.getRelationByUserIds(userService.getAuthenticatedUser().getId(), fetchedUser.get().getId());
                redirectAttributes.addFlashAttribute("foundUser", fetchedUser.get());
                redirectAttributes.addFlashAttribute("isBuddy", relation.isPresent());
            } else {
                redirectAttributes.addFlashAttribute("foundUser", null);
            }
            return "redirect:/home";
        } catch (IllegalArgumentException | UsernameNotFoundException e) {}
        return "home";
    }

  /*  @RequestMapping(value="/relation/add-buddy")
    public void addBuddy(Model model) {
        String foundUser = (String) model.asMap().get("foundUser");
    }*/
}
