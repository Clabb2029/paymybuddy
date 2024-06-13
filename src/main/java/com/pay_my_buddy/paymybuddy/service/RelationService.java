package com.pay_my_buddy.paymybuddy.service;

import com.pay_my_buddy.paymybuddy.model.Relation;
import com.pay_my_buddy.paymybuddy.model.User;
import com.pay_my_buddy.paymybuddy.repository.RelationRepository;
import com.pay_my_buddy.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RelationService {

    @Autowired
    private RelationRepository relationRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getLastAddedRelationOfUser(Integer id) {
        return relationRepository.findLastAddedRelationOfUser(id);
    }

    public Optional<Relation> getRelationByUserIds(Integer sender_id, Integer beneficiary_id) {
        return relationRepository.findRelationByUserIds(sender_id, beneficiary_id);
    }

    public void addBuddy(Integer beneficiaryId, Integer senderId) {
        User beneficiary = userRepository.findById(beneficiaryId).get();
        User sender = userRepository.findById(senderId).get();
        Relation relation = new Relation(beneficiary, sender);
        relationRepository.save(relation);
    }

    public Iterable<Relation> getRelationsOfUser(Integer id) {
        return relationRepository.findRelationsOfUser(id);
    }

}
