package com.pay_my_buddy.paymybuddy.repository;

import com.pay_my_buddy.paymybuddy.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

    Optional<User> findByEmail(String email);

    @Query("FROM User WHERE email = ?1 AND password = ?2")
    User findByEmailAndPassword(String email, String password);

    @Query("FROM User WHERE email = ?2 AND email != ?1")
    Optional<User> findUserByEmailOtherThanCurrentUser(String currentUserEmail, String emailSearched);

}
