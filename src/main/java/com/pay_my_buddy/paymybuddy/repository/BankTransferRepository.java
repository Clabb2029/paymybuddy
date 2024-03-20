package com.pay_my_buddy.paymybuddy.repository;

import com.pay_my_buddy.paymybuddy.model.BankTransfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTransferRepository extends CrudRepository<BankTransfer, Integer> {
}
