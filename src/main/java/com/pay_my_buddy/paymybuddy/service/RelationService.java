package com.pay_my_buddy.paymybuddy.service;

import com.pay_my_buddy.paymybuddy.model.Relation;
import com.pay_my_buddy.paymybuddy.model.User;
import com.pay_my_buddy.paymybuddy.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class RelationService {

    @Autowired
    private RelationRepository relationRepository;

    public Optional<User> getLastAddedRelationOfUser(Integer id) {
        return relationRepository.findLastAddedRelationOfUser(id);
    }

    public Optional<Relation> getRelationByUserIds(Integer sender_id, Integer beneficiary_id) {
        return relationRepository.findRelationByUserIds(sender_id, beneficiary_id);
    }

    public void addBuddy(User beneficiary, User sender) {
        Relation relation = new Relation(beneficiary, sender);
        relationRepository.save(relation);
    }

    public Iterable<Relation> getRelationsOfUser(Integer id) {
        return relationRepository.findRelationsOfUser(id);
    }

}
