package com.pay_my_buddy.paymybuddy.controller;

import com.pay_my_buddy.paymybuddy.DTO.UserDTO;
import com.pay_my_buddy.paymybuddy.model.Relation;
import com.pay_my_buddy.paymybuddy.model.User;
import com.pay_my_buddy.paymybuddy.service.RelationService;
import com.pay_my_buddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("relation/add-buddy")
    public String addBuddy(@RequestParam("foundUserId") Integer foundUserId, RedirectAttributes redirectAttributes) {
        User beneficiary = userService.getUserById(foundUserId).get();
        UserDTO currentUser = userService.getAuthenticatedUser();
        relationService.addBuddy(beneficiary.getId(), currentUser.getId());
        redirectAttributes.addFlashAttribute("newBuddy", beneficiary);
        redirectAttributes.addFlashAttribute("buddyAdded", true);
        redirectAttributes.addFlashAttribute("toastText", beneficiary.getUserFullname() + " has been successfully added to your buddies list.");
        return "redirect:/home";
    }
}
