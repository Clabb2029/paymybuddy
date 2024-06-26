package com.pay_my_buddy.paymybuddy.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TransferDTO {

    private Double commission;
    private LocalDateTime date;
    private String description;
    private Integer beneficiaryId;
    private String beneficiary_lastname;
    private String beneficiary_firstname;
    private Integer senderId;
    private String sender_lastname;
    private String sender_firstname;
    private Float amount;

    @Getter
    private String beneficiaryFullname;
    @Getter
    private String senderFullname;

    public TransferDTO(Double commission, LocalDateTime date, String description, Integer beneficiaryId, String beneficiary_lastname, String beneficiary_firstname, Integer senderId, String sender_lastname, String sender_firstname, Float amount) {
        this.commission = commission;
        this.date = date;
        this.description = description;
        this.beneficiaryId = beneficiaryId;
        this.beneficiary_lastname = beneficiary_lastname;
        this.beneficiary_firstname = beneficiary_firstname;
        this.senderId = senderId;
        this.sender_lastname = sender_lastname;
        this.sender_firstname = sender_firstname;
        this.amount = amount;
        setBeneficiaryFullname(beneficiary_firstname, beneficiary_lastname);
        setSenderFullname(sender_firstname, sender_lastname);
    }

    public void setBeneficiaryFullname(String beneficiary_firstname, String beneficiary_lastname) {
        this.beneficiaryFullname = beneficiary_firstname + ' ' + beneficiary_lastname;
    }

    public void setSenderFullname(String sender_firstname, String sender_lastname) {
        this.senderFullname = sender_firstname + ' ' + sender_lastname;
    }
}