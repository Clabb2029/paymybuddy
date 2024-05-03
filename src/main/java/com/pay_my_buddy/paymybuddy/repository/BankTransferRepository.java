package com.pay_my_buddy.paymybuddy.repository;

import com.pay_my_buddy.paymybuddy.model.BankTransfer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTransferRepository extends CrudRepository<BankTransfer, Integer> {

    @Query("FROM BankTransfer AS bt " +
            "JOIN User as u " +
            "ON bt.user.id = u.id " +
            "WHERE bt.user.id = ?1 " +
            "ORDER BY bt.date DESC")
    Iterable<BankTransfer> findBankTransfersOfUser(Integer id);
}
