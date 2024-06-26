package com.pay_my_buddy.paymybuddy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "Bank_Transfer")
@Data
@ToString(callSuper = true)
public class BankTransfer extends Transaction {

    @Column(name = "bank_account_number")
    @NotNull(message = "You need to specify your bank account number")
    @Size(min = 14, message = "Your bank account number must be at least 14 characters long")
    @Size(max = 34, message = "Your bank account number must be a maximum of 34 characters")
    private String bankAccountNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
