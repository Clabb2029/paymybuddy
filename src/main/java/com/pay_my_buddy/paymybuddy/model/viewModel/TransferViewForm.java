package com.pay_my_buddy.paymybuddy.model.viewModel;

import lombok.Data;

@Data
public class TransferViewForm {

    private Integer beneficiaryId;
    private String description;
    private Float amount;

    public TransferViewForm(Integer beneficiaryId, String description, Float amount) {
        this.beneficiaryId = beneficiaryId;
        this.description = description;
        this.amount = amount;
    }

    public TransferViewForm() {}
}
