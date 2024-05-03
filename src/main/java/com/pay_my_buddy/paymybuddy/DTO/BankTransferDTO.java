package com.pay_my_buddy.paymybuddy.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class BankTransferDTO {

    private Date date;
    private String description;
    private Float amount;

    public BankTransferDTO(Date date, String description, Float amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }
}
