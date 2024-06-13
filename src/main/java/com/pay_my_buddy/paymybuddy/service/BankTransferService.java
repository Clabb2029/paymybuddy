package com.pay_my_buddy.paymybuddy.service;

import com.pay_my_buddy.paymybuddy.DTO.BankTransferDTO;
import com.pay_my_buddy.paymybuddy.DTO.UserDTO;
import com.pay_my_buddy.paymybuddy.model.BankTransfer;
import com.pay_my_buddy.paymybuddy.model.User;
import com.pay_my_buddy.paymybuddy.repository.BankTransferRepository;
import com.pay_my_buddy.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Function;

@Service
public class BankTransferService {

    @Autowired
    private BankTransferRepository bankTransferRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public Page<BankTransferDTO> getBankTransfersOfUser(Integer id, PageRequest pageRequest) {
        Page<BankTransfer> bankTransferPage = bankTransferRepository.findBankTransfersOfUser(id, pageRequest);
        return bankTransferPage.map(new Function<BankTransfer, BankTransferDTO>() {
            @Override
            public BankTransferDTO apply(BankTransfer bankTransfer) {
                return new BankTransferDTO(
                        bankTransfer.getDate(),
                        bankTransfer.getDescription(),
                        bankTransfer.getAmount()
                );
            }
        });
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
