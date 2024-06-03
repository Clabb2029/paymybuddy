package com.pay_my_buddy.paymybuddy.service;

import com.pay_my_buddy.paymybuddy.DTO.UserDTO;
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
        user.setFirstname(user.getFirstname().substring(0, 1).toUpperCase() + user.getFirstname().substring(1));
        user.setLastname(user.getLastname().substring(0, 1).toUpperCase() + user.getLastname().substring(1));
        user.setPassword(hashedPassword);
        user.setIsActive(true);
        user.setBalance((float) 0);
        userRepository.save(user);
    }

    public UserDTO getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(email.isEmpty()) {
            throw new UsernameNotFoundException("Email not found");
        }
        User user = userRepository.findByEmail(email).get();
        return new UserDTO(user.getId(), user.getLastname(), user.getFirstname(), user.getEmail(), user.getBalance());
    }

    public User saveUser(UserProfileViewForm user) {
        User loggedUser = userRepository.findByEmail(user.getEmail()).get();
        loggedUser.setFirstname(user.getFirstname().substring(0, 1).toUpperCase() + user.getFirstname().substring(1));
        loggedUser.setLastname(user.getLastname().substring(0, 1).toUpperCase() + user.getLastname().substring(1));
        return userRepository.save(loggedUser);
    }
}
