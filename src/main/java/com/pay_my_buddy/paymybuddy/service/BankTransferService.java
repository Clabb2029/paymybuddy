package com.pay_my_buddy.paymybuddy.service;

import com.pay_my_buddy.paymybuddy.model.BankTransfer;
import com.pay_my_buddy.paymybuddy.repository.BankTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankTransferService {

    @Autowired
    private BankTransferRepository bankTransferRepository;

    public Iterable<BankTransfer> getBankTransfers() {
        return bankTransferRepository.findAll();
    }
}
