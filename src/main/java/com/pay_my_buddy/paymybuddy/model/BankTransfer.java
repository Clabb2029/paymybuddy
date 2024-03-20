package com.pay_my_buddy.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Bank_Transfer")
@Data
public class BankTransfer extends Transaction {

    @Column(name = "bank_account_number")
    private Integer bankAccountNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
