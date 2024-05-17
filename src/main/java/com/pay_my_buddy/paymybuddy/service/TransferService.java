package com.pay_my_buddy.paymybuddy.service;

import com.pay_my_buddy.paymybuddy.DTO.TransferDTO;
import com.pay_my_buddy.paymybuddy.exception.EmailAlreadyExistingException;
import com.pay_my_buddy.paymybuddy.exception.InsufficientBalanceException;
import com.pay_my_buddy.paymybuddy.model.Relation;
import com.pay_my_buddy.paymybuddy.model.Transfer;
import com.pay_my_buddy.paymybuddy.model.User;
import com.pay_my_buddy.paymybuddy.model.viewModel.TransferViewForm;
import com.pay_my_buddy.paymybuddy.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RelationService relationService;

    public ArrayList<TransferDTO> getTransfersOfUser(Integer id) {
        Iterable<Transfer> transferList = transferRepository.findTransfersOfUser(id);
        ArrayList<TransferDTO> transferDTOList = new ArrayList<>();
        for (Transfer transfer : transferList) {
            TransferDTO transferDTO = new TransferDTO(
                    transfer.getCommission(),
                    transfer.getDate(),
                    transfer.getDescription(),
                    transfer.getRelation().getBeneficiary().getLastname(),
                    transfer.getRelation().getBeneficiary().getFirstname(),
                    transfer.getRelation().getSender().getLastname(),
                    transfer.getRelation().getSender().getFirstname(),
                    transfer.getAmount()
            );
            if (Objects.equals(transfer.getRelation().getSender().getId(), userService.getAuthenticatedUser().getId())) {
                transferDTO.setAmount(transferDTO.getAmount()*-1);
            }
            transferDTOList.add(transferDTO);
        }
        return transferDTOList;
    }

    @Transactional
    public void sendMoney(Integer currentUserId, TransferViewForm transfer) {
        Float currentUserBalance = userService.getAuthenticatedUser().getBalance();
        if(currentUserBalance < transfer.getAmount()) {
            throw new InsufficientBalanceException("The amount you entered is greater than your balance. Please enter a lower amount.");
        }
        Relation relation = relationService.getRelationByUserIds(currentUserId, transfer.getBeneficiaryId()).get();

        // Transfer saving
        Float amount = (float) (transfer.getAmount() - (transfer.getAmount() * 0.05));
        Transfer transferToSave = new Transfer();
        transferToSave.setDate(LocalDateTime.now());
        transferToSave.setAmount(transfer.getAmount());
        transferToSave.setCommission(0.05F);
        transferToSave.setDescription(transfer.getDescription());
        transferToSave.setRelation(relation);
        transferRepository.save(transferToSave);

        // Beneficiary balance updating
        User beneficiary = relation.getBeneficiary();
        beneficiary.setBalance(beneficiary.getBalance() + amount);
        userService.saveUser(beneficiary);

        // Sender balance updating
        User sender = relation.getSender();
        sender.setBalance(sender.getBalance() - transfer.getAmount());
        userService.saveUser(sender);
    }
}
