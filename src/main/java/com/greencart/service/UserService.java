package com.greencart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.greencart.model.User;
import com.greencart.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> signup(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Email already exists");
        }
        userRepository.save(user);
        return ResponseEntity.ok("Signup successful");
    }

    public ResponseEntity<?> login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("User not found");
        }

        if (!user.getPassword().equals(password)) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid email or password");
        }

        return ResponseEntity.ok("Login successful");
    }
}
