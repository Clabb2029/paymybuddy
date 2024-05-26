package com.pay_my_buddy.paymybuddy.service;

import com.pay_my_buddy.paymybuddy.model.User;
import com.pay_my_buddy.paymybuddy.exception.EmailAlreadyExistingException;
import com.pay_my_buddy.paymybuddy.model.viewModel.UserProfileViewForm;
import com.pay_my_buddy.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = false)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }


    public Optional<User> getUserByEmailOtherThanCurrentUser(String currentUserEmail, String emailSearched) {
        return userRepository.findUserByEmailOtherThanCurrentUser(currentUserEmail, emailSearched);
    }

    @Transactional
    public void createUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser.isPresent()) {
            throw new EmailAlreadyExistingException("An account with this email address already exists. Please use another one");
        }
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setIsActive(true);
        user.setBalance((float) 0);
        userRepository.save(user);
    }

    public User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(email.isEmpty()) {
            throw new UsernameNotFoundException("Email not found");
        }
        return userRepository.findByEmail(email).get();
    }

    public User saveUser(UserProfileViewForm user) {
        User loggedUser = getAuthenticatedUser();
        loggedUser.setFirstname(user.getFirstname());
        loggedUser.setLastname(user.getLastname());
        System.out.println("logged user " + loggedUser);
        return userRepository.save(loggedUser);
    }
}
