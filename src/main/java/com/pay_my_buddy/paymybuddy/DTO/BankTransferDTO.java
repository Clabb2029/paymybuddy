package com.pay_my_buddy.paymybuddy.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BankTransferDTO {

    private LocalDateTime date;
    private String description;
    private Float amount;

    public BankTransferDTO(LocalDateTime date, String description, Float amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }
}
