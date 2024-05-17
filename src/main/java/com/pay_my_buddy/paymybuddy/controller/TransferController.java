package com.pay_my_buddy.paymybuddy.controller;

import com.pay_my_buddy.paymybuddy.DTO.BankTransferDTO;
import com.pay_my_buddy.paymybuddy.DTO.TransferDTO;
import com.pay_my_buddy.paymybuddy.model.BankTransfer;
import com.pay_my_buddy.paymybuddy.model.Relation;
import com.pay_my_buddy.paymybuddy.model.User;
import com.pay_my_buddy.paymybuddy.model.viewModel.TransferViewForm;
import com.pay_my_buddy.paymybuddy.service.BankTransferService;
import com.pay_my_buddy.paymybuddy.service.RelationService;
import com.pay_my_buddy.paymybuddy.service.TransferService;
import com.pay_my_buddy.paymybuddy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@SuppressWarnings("UnresolvedClassReferenceRepair")
@Controller
public class TransferController {

    @Autowired
    private TransferService transferService;

    @Autowired
    private BankTransferService bankTransferService;

    @Autowired
    private UserService userService;

    @Autowired
    private RelationService relationService;

    @GetMapping("/transfer")
    public String showTransferList(Model model) {
        model.addAttribute("activePage", "transfer");
        model.addAttribute("titlePage", "Transfer");
        model.addAttribute("selectedTab", "transfer");
        User currentUser = userService.getAuthenticatedUser();
        ArrayList<TransferDTO> transferDTOList = transferService.getTransfersOfUser(userService.getAuthenticatedUser().getId());
        Iterable<Relation> relationList = relationService.getRelationsOfUser(currentUser.getId());
        model.addAttribute("transfers", transferDTOList);
        model.addAttribute("currentUserFullname", userService.getAuthenticatedUser().getUserFullname());
        model.addAttribute("transfer", new TransferViewForm());
        model.addAttribute("userRelations", relationList);
        return "transfer";
    }

    @GetMapping("/bank-transfer")
    public String showBankTransferList(Model model) {
        model.addAttribute("activePage", "transfer");
        model.addAttribute("titlePage", "Transfer");
        model.addAttribute("selectedTab", "banktransfer");
        model.addAttribute("bankTransfer", new BankTransfer());
        ArrayList<BankTransferDTO> bankTransferDTOList = bankTransferService.getBankTransfersOfUser(userService.getAuthenticatedUser().getId());
        model.addAttribute("bankTransfers", bankTransferDTOList);
        return "transfer";
    }

    @PostMapping("/transfer/send-money")
    public String sendMoney(@ModelAttribute("transfer") TransferViewForm transfer) {
        Integer currentUserId = userService.getAuthenticatedUser().getId();
        transferService.sendMoney(currentUserId, transfer);
        return "redirect:/transfer";
    }

    @PostMapping("/transfer/add-money")
    public String addMoney(@Valid @ModelAttribute("bankTransfer") BankTransfer bankTransfer, RedirectAttributes redirectAttributes) {
        bankTransferService.addMoney(bankTransfer);
        redirectAttributes.addFlashAttribute("added", "Your payment has been successfully made");
        return "redirect:/bank-transfer";
    }
}
