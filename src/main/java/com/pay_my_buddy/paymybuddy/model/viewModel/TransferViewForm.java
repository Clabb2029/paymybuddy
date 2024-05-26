package com.pay_my_buddy.paymybuddy.model.viewModel;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferViewForm {

    private Integer beneficiaryId;
    private String description;

    @NotNull(message = "You need to specify the amount")
    private Float amount;

    public TransferViewForm(Integer beneficiaryId, String description, Float amount) {
        this.beneficiaryId = beneficiaryId;
        this.description = description;
        this.amount = amount;
    }

    public TransferViewForm() {}
}
