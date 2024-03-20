package com.pay_my_buddy.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "User")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String lastname;

    @Column
    private String firstname;

    @Column
    private String email;

    @Column
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
