package com.pay_my_buddy.paymybuddy.service;

import com.pay_my_buddy.paymybuddy.DTO.TransferDTO;
import com.pay_my_buddy.paymybuddy.exception.InsufficientBalanceException;
import com.pay_my_buddy.paymybuddy.model.Relation;
import com.pay_my_buddy.paymybuddy.model.Transfer;
import com.pay_my_buddy.paymybuddy.model.User;
import com.pay_my_buddy.paymybuddy.model.viewModel.TransferViewForm;
import com.pay_my_buddy.paymybuddy.repository.TransferRepository;
import com.pay_my_buddy.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.function.Function;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RelationService relationService;

    @Autowired
    private UserRepository userRepository;

    private Double commission = 0.05;

    public Page<TransferDTO> getTransfersOfUser(Integer id, PageRequest pageRequest) {
        Page<Transfer> transferPage = transferRepository.findTransfersOfUser(id, pageRequest);
        return transferPage.map(new Function<Transfer, TransferDTO>() {
            @Override
            public TransferDTO apply(Transfer transfer) {
                return new TransferDTO(
                        transfer.getCommission(),
                        transfer.getDate(),
                        transfer.getDescription(),
                        transfer.getRelation().getBeneficiary().getId(),
                        transfer.getRelation().getBeneficiary().getLastname(),
                        transfer.getRelation().getBeneficiary().getFirstname(),
                        transfer.getRelation().getSender().getId(),
                        transfer.getRelation().getSender().getLastname(),
                        transfer.getRelation().getSender().getFirstname(),
                        transfer.getAmount()
                );
            }
        });
    }

    @Transactional
    public void sendMoney(Integer currentUserId, TransferViewForm transfer) {
        Float currentUserBalance = userService.getAuthenticatedUser().getBalance();
        if(currentUserBalance < transfer.getAmount()) {
            throw new InsufficientBalanceException("The amount you entered is greater than your balance. Please enter a lower amount.");
        }
        Relation relation = relationService.getRelationByUserIds(currentUserId, transfer.getBeneficiaryId()).get();

        // Transfer saving
        Float amount = (float) (transfer.getAmount() - (transfer.getAmount() * commission));
        Transfer transferToSave = new Transfer();
        transferToSave.setDate(LocalDateTime.now());
        transferToSave.setAmount(transfer.getAmount());
        transferToSave.setCommission(commission);
        transferToSave.setDescription(transfer.getDescription());
        transferToSave.setRelation(relation);
        transferRepository.save(transferToSave);


        // Beneficiary balance updating
        User beneficiary = relation.getBeneficiary();
        beneficiary.setBalance(beneficiary.getBalance() + amount);
        userRepository.save(beneficiary);

        // Sender balance updating
        User sender = relation.getSender();
        sender.setBalance(sender.getBalance() - transfer.getAmount());
        userRepository.save(sender);
    }
}
