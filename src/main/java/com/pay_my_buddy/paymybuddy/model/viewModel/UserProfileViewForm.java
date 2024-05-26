package com.pay_my_buddy.paymybuddy.model.viewModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserProfileViewForm {

    @NotBlank(message = "Please fill the lastname field")
    private String lastname;

    @NotBlank(message = "Please fill the firstname field")
    private String firstname;

    private String email;

    public UserProfileViewForm(String lastname, String firstname, String email) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
    }
}
