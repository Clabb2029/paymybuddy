package com.pay_my_buddy.paymybuddy.service;

import com.pay_my_buddy.paymybuddy.DTO.BankTransferDTO;
import com.pay_my_buddy.paymybuddy.model.BankTransfer;
import com.pay_my_buddy.paymybuddy.repository.BankTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BankTransferService {

    @Autowired
    private BankTransferRepository bankTransferRepository;

    public ArrayList<BankTransferDTO> getBankTransfersOfUser(Integer id) {
        Iterable<BankTransfer> bankTransferList = bankTransferRepository.findBankTransfersOfUser(id);
        ArrayList<BankTransferDTO> bankTransferDTOList = new ArrayList<>();
        for (BankTransfer bankTransfer : bankTransferList) {
            BankTransferDTO bankTransferDTO = new BankTransferDTO(
                    bankTransfer.getDate(),
                    bankTransfer.getDescription(),
                    bankTransfer.getAmount()
            );
            bankTransferDTOList.add(bankTransferDTO);
        }
        return bankTransferDTOList;
    }
}
