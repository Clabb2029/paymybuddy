package com.pay_my_buddy.paymybuddy.repository;

import com.pay_my_buddy.paymybuddy.DTO.Relation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationRepository extends CrudRepository<Relation, Integer> {
}
