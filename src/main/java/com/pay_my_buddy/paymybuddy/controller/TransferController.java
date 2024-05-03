package com.pay_my_buddy.paymybuddy.controller;

import com.pay_my_buddy.paymybuddy.DTO.BankTransferDTO;
import com.pay_my_buddy.paymybuddy.DTO.TransferDTO;
import com.pay_my_buddy.paymybuddy.model.Transfer;
import com.pay_my_buddy.paymybuddy.service.BankTransferService;
import com.pay_my_buddy.paymybuddy.service.TransferService;
import com.pay_my_buddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TransferController {

    @Autowired
    private TransferService transferService;

    @Autowired
    private BankTransferService bankTransferService;

    @Autowired
    private UserService userService;

    @GetMapping("/transfer")
    public String showTransferList(Model model) {
        model.addAttribute("activePage", "transfer");
        model.addAttribute("titlePage", "Transfer");
        model.addAttribute("selectedTab", "transfer");
        ArrayList<TransferDTO> transferDTOList = transferService.getTransfersOfUser(userService.getAuthenticatedUser().getId());
        model.addAttribute("transfers", transferDTOList);
        model.addAttribute("currentUserFullname", userService.getAuthenticatedUser().getUserFullname());
        return "transfer";
    }

    @GetMapping("/bank-transfer")
    public String showBankTransferList(Model model) {
        model.addAttribute("activePage", "transfer");
        model.addAttribute("titlePage", "Transfer");
        model.addAttribute("selectedTab", "banktransfer");
        ArrayList<BankTransferDTO> bankTransferDTOList = bankTransferService.getBankTransfersOfUser(userService.getAuthenticatedUser().getId());
        model.addAttribute("bankTransfers", bankTransferDTOList);
        return "transfer";
    }
}
