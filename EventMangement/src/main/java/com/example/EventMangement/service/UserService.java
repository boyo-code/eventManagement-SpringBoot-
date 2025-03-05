package com.example.EventMangement.service;

import com.example.EventMangement.Repository.UserRepository;
import com.example.EventMangement.model.User;
import com.example.EventMangement.model.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(@Valid User user) {
        // Check if username or email already exists
        if (userRepository.findByName(user.getName()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        
        // Set default role if not specified
        if (user.getRole() == null) {
            user.setRole(Role.ATTENDEE);
        }
        
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public User updateUser(Long userId, @Valid User userDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Check if new username is taken by another user
        if (!user.getName().equals(userDetails.getName()) &&
            userRepository.findByName(userDetails.getName()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        
        // Check if new email is taken by another user
        if (!user.getEmail().equals(userDetails.getEmail()) &&
            userRepository.findByEmail(userDetails.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        
        // Update fields
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        if (userDetails.getRole() != null) {
            user.setRole(userDetails.getRole());
        }
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }
}