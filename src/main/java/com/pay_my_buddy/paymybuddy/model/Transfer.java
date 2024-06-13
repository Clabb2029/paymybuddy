package com.pay_my_buddy.paymybuddy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Transfer")
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Transfer extends Transaction{

    private Double commission;

    @OneToOne
    @JoinColumn(name = "relation_id", referencedColumnName = "id")
    private Relation relation = new Relation();

}
