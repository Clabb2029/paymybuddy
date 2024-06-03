package com.pay_my_buddy.paymybuddy.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    private String lastname;
    private String firstname;
    private String email;
    private Float balance;

    public String getUserFullname() {
        return firstname + ' ' + lastname;
    }
}
