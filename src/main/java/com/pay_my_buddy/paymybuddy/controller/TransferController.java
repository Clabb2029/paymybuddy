package com.pay_my_buddy.paymybuddy.controller;

import com.pay_my_buddy.paymybuddy.DTO.BankTransferDTO;
import com.pay_my_buddy.paymybuddy.DTO.TransferDTO;
import com.pay_my_buddy.paymybuddy.DTO.UserDTO;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

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
        Integer currentUserId = userService.getAuthenticatedUser().getId();
        ArrayList<TransferDTO> transferDTOList = transferService.getTransfersOfUser(currentUserId);
        Iterable<Relation> relationList = relationService.getRelationsOfUser(currentUserId);
        model.addAttribute("transfers", transferDTOList);
        model.addAttribute("currentUserId", currentUserId);
        model.addAttribute("transfer", new TransferViewForm());
        model.addAttribute("draftTransfer", null);
        model.addAttribute("userRelations", relationList);
        return "transfer";
    }

    @GetMapping("/bank-transfer")
    public String showBankTransferList(Model model) {
        model.addAttribute("activePage", "transfer");
        model.addAttribute("titlePage", "Transfer");
        model.addAttribute("selectedTab", "banktransfer");
        model.addAttribute("bankTransfer", new BankTransfer());
        model.addAttribute("draftBankTransfer", null);
        ArrayList<BankTransferDTO> bankTransferDTOList = bankTransferService.getBankTransfersOfUser(userService.getAuthenticatedUser().getId());
        model.addAttribute("bankTransfers", bankTransferDTOList);
        return "transfer";
    }

    @PostMapping("/transfer/send-money")
    public String sendMoney(@Valid @ModelAttribute("transfer") TransferViewForm transfer, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if(bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            if(errors.size() > 1) {
                redirectAttributes.addFlashAttribute("toastErrorText", "Please select a buddy and fill the amount field");
            } else {
                redirectAttributes.addFlashAttribute("toastErrorText", errors.get(0).getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute("draftTransfer", transfer);
            return "redirect:/transfer";
        }
        Integer currentUserId = userService.getAuthenticatedUser().getId();
        transferService.sendMoney(currentUserId, transfer);
        redirectAttributes.addFlashAttribute("toastSuccessText", "Your payment has been successfully made");
        return "redirect:/transfer";
    }

    @PostMapping("/transfer/add-money")
    public String addMoney(@Valid @ModelAttribute("bankTransfer") BankTransfer bankTransfer, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if(bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            if(errors.size() > 1) {
                redirectAttributes.addFlashAttribute("toastErrorText", "Please fill the amount and bank account number fields");
            } else {
                redirectAttributes.addFlashAttribute("toastErrorText", errors.get(0).getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute("draftBankTransfer", bankTransfer);
            return "redirect:/bank-transfer";
        }
        bankTransferService.addMoney(bankTransfer);
        redirectAttributes.addFlashAttribute("toastSuccessText", "Your payment has been successfully made");
        model.addAttribute("selectedTab", "banktransfer");
        return "redirect:/bank-transfer";
    }
}
