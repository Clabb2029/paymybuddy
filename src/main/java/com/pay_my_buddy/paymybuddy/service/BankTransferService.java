package com.pay_my_buddy.paymybuddy.service;

import com.pay_my_buddy.paymybuddy.DTO.BankTransferDTO;
import com.pay_my_buddy.paymybuddy.DTO.UserDTO;
import com.pay_my_buddy.paymybuddy.model.BankTransfer;
import com.pay_my_buddy.paymybuddy.model.User;
import com.pay_my_buddy.paymybuddy.repository.BankTransferRepository;
import com.pay_my_buddy.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Service
public class BankTransferService {

    @Autowired
    private BankTransferRepository bankTransferRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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

    @Transactional
    public void addMoney(BankTransfer bankTransfer) {
        UserDTO currentUser = userService.getAuthenticatedUser();
        User user = userRepository.findById(currentUser.getId()).get();
        user.setBalance(user.getBalance() + bankTransfer.getAmount());
        bankTransfer.setUser(user);
        bankTransfer.setDate(LocalDateTime.now());
        bankTransferRepository.save(bankTransfer);
        userRepository.save(user);
    }
}
