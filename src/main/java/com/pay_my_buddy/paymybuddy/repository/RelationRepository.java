package com.pay_my_buddy.paymybuddy.repository;

import com.pay_my_buddy.paymybuddy.model.Relation;
import com.pay_my_buddy.paymybuddy.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RelationRepository extends CrudRepository<Relation, Integer> {

    @Query(value = "SELECT u FROM Relation AS r JOIN User AS u ON r.beneficiary.id = u.id WHERE r.sender.id = ?1 ORDER BY r.id DESC LIMIT 1")
    Optional<User> findLastAddedRelationOfUser(Integer id);

    @Query("FROM Relation WHERE sender.id = ?1 AND beneficiary.id = ?2")
    Optional <Relation> findRelationByUserIds(Integer sender_id, Integer beneficiary_id);

    @Query("FROM Relation AS r JOIN User as u ON r.beneficiary.id = u.id WHERE r.sender.id = ?1")
    Iterable<Relation> findRelationsOfUser(Integer id);
}
