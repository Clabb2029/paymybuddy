package com.pay_my_buddy.paymybuddy.repository;

import com.pay_my_buddy.paymybuddy.DTO.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("FROM User WHERE id != ?1")
    Iterable<User> findAllExceptCurrentUser(Integer id);

    Optional<User> findByEmail(String email);

    @Query("FROM User WHERE email = ?1 AND password = ?2")
    User findByEmailAndPassword(String email, String password);
}
