package com.pay_my_buddy.paymybuddy.repository;

import com.pay_my_buddy.paymybuddy.model.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends CrudRepository<Transfer, Integer> {

    @Query("FROM Transfer AS t " +
            "JOIN Relation AS r " +
            "ON r.id = t.relation.id " +
            "JOIN User AS u1 " +
            "ON r.beneficiary.id = u1.id " +
            "JOIN User AS u2 " +
            "ON r.sender.id = u2.id " +
            "WHERE r.sender.id = ?1 " +
            "OR r.beneficiary.id = ?1 " +
            "ORDER BY t.date DESC")
    Page<Transfer> findTransfersOfUser(Integer id, Pageable pageable);
}
