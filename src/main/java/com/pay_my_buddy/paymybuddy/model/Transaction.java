package com.pay_my_buddy.paymybuddy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@MappedSuperclass
@Data
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date date;

    @NotNull(message = "You need to specify the amount")
    @Min(value = 1, message = "The amount should be greater than 0")
    private Float amount;
    private String description;

}
