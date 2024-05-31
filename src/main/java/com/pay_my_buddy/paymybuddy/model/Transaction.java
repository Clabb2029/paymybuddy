package com.pay_my_buddy.paymybuddy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime date;

    @NotNull(message = "You need to specify the amount")
    @Min(value = 1, message = "The amount should be greater than 0")
    private Float amount;

    @Size(max = 80)
    private String description;

}
