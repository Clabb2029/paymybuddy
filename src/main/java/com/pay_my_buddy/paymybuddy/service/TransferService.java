package com.pay_my_buddy.paymybuddy.service;

import com.pay_my_buddy.paymybuddy.model.Transfer;
import com.pay_my_buddy.paymybuddy.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    public Iterable<Transfer> getTransfers() {
        return transferRepository.findAll();
    }
}
