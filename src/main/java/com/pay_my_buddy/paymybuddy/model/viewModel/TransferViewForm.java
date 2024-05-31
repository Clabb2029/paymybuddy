package com.pay_my_buddy.paymybuddy.model.viewModel;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TransferViewForm {

    @NotNull(message = "You need to specify the buddy")
    private Integer beneficiaryId;

    @Size(max = 80, message = "You need to specify the amount")
    private String description;

    @NotNull(message = "Please write a shorter description. Maximum: 80 characters")
    private Float amount;

    public TransferViewForm(Integer beneficiaryId, String description, Float amount) {
        this.beneficiaryId = beneficiaryId;
        this.description = description;
        this.amount = amount;
    }

    public TransferViewForm() {}
}
