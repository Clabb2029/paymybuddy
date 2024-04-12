package com.pay_my_buddy.paymybuddy.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "User")
@Data
@DynamicUpdate
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

    public User(Integer id, String lastname, String firstname, String email, String password, Float balance, boolean isActive) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.isActive = isActive;
    }

    public User() {}
}
