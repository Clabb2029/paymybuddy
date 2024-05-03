package com.pay_my_buddy.paymybuddy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "User")
@Data
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    @NotBlank(message = "Please fill the lastname field")
    private String lastname;

    @Column
    @NotBlank(message = "Please fill the firstname field")
    private String firstname;

    @Column
    @NotBlank(message = "Please fill the email field")
    @Email(message = "Email should be valid")
    private String email;

    @Column
    @NotBlank(message = "Please fill the password field")
    private String password;

    @Column
    private Float balance;

    @Column(name = "is_active")
    private Boolean isActive;

    public String getUserFullname() {
        return firstname + ' ' + lastname;
    }
}
