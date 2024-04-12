package com.pay_my_buddy.paymybuddy.service;

import com.pay_my_buddy.paymybuddy.DTO.User;
import com.pay_my_buddy.paymybuddy.exception.EmailAlreadyExistingException;
import com.pay_my_buddy.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void createUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser.isPresent()) {
            throw new EmailAlreadyExistingException("An account with this email address already exists. Please use another one");
        }
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        System.out.println("user : " + user);
        System.out.println("hashed PW : " + hashedPassword);
        user.setPassword(hashedPassword);
        System.out.println("user with HW : " + user);
        user.setIsActive(true);
        user.setBalance((float) 0);
        System.out.println("user with all : " + user);
        userRepository.save(user);
    }

    public Iterable<User> getAllUsersExceptCurrentUser(Integer id) {
        return userRepository.findAllExceptCurrentUser(id);
    }

    public User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(email.isEmpty()) {
            throw new UsernameNotFoundException("Email not found");
        }
        return userRepository.findByEmail(email).get();
    }

    public User getUserByLoginInfo(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

}
