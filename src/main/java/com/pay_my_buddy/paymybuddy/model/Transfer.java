package com.pay_my_buddy.paymybuddy.model;

import com.pay_my_buddy.paymybuddy.DTO.TransferDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name ="Transfer")
@Data
@ToString(callSuper = true)
@DynamicUpdate
public class Transfer extends Transaction{

    private Float commission;

    @OneToOne
    @JoinColumn(name = "relation_id", referencedColumnName = "id")
    private Relation relation = new Relation();

}
