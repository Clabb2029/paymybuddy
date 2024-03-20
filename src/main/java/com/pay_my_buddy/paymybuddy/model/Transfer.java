package com.pay_my_buddy.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="Transfer")
@Data
public class Transfer extends Transaction{

    private Float commission;

    @OneToOne
    @JoinColumn(name = "relation_id", referencedColumnName = "id")
    private Relation relation = new Relation();

}
