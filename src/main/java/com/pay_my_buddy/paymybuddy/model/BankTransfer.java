package com.pay_my_buddy.paymybuddy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "Bank_Transfer")
@Data
@ToString(callSuper = true)
public class BankTransfer extends Transaction {

    @Column(name = "bank_account_number")
    @NotNull(message = "You need to specify your bank account number")
    private String bankAccountNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
