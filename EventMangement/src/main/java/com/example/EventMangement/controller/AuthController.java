package com.example.EventMangement.controller;

import com.example.EventMangement.model.User;
import com.example.EventMangement.Repository.UserRepository;
import com.example.EventMangement.Security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<String> register(@RequestBody User user) {
        // Check if the username already exists
        if (userRepository.findByName(user.getName()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByName(user.getName());

        // Check if user exists and passwords match
        if (existingUser.isPresent() && passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            String token = jwtUtil.generateToken(existingUser.get().getName());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}

