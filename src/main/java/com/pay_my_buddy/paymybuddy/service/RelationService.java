package com.pay_my_buddy.paymybuddy.service;

import com.pay_my_buddy.paymybuddy.DTO.Relation;
import com.pay_my_buddy.paymybuddy.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationService {

    @Autowired
    private RelationRepository relationRepository;

    public Iterable<Relation> getRelations() {
        return relationRepository.findAll();
    }
}
