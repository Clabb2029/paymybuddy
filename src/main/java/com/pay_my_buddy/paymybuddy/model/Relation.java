package com.pay_my_buddy.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Relation")
@Data
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "beneficiary_id", referencedColumnName = "id")
    private User beneficiary;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    public Relation(User beneficiary, User sender) {
        this.beneficiary = beneficiary;
        this.sender = sender;
    }

    public Relation() {}
}
