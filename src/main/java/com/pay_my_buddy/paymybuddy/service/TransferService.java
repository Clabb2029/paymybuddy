package com.pay_my_buddy.paymybuddy.service;

import com.pay_my_buddy.paymybuddy.DTO.TransferDTO;
import com.pay_my_buddy.paymybuddy.model.Transfer;
import com.pay_my_buddy.paymybuddy.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private UserService userService;

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
}
