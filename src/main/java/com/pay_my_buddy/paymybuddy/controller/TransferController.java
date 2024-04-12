package com.pay_my_buddy.paymybuddy.controller;

import com.pay_my_buddy.paymybuddy.DTO.Transfer;
import com.pay_my_buddy.paymybuddy.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransferController {

    @Autowired
    private TransferService transferService;

    @GetMapping("/transfer")
    public String showTransferList(Model model) {
        Iterable<Transfer> transferList = transferService.getTransfers();
        System.out.println(transferList);
        model.addAttribute("transfers", transferList);
        return "transfer";
    }
}
