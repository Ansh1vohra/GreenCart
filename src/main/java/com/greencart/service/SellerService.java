package com.greencart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.greencart.model.Seller;
import com.greencart.repository.SellerRepository;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    // 🟢 SIGNUP
    public ResponseEntity<?> signup(Seller seller) {
        if (sellerRepository.findByEmail(seller.getEmail()) != null) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Email already exists");
        }

        Seller savedSeller = sellerRepository.save(seller);

        // Hide password before sending back
        savedSeller.setPassword(null);

        return ResponseEntity.ok(savedSeller);
    }

    // 🟢 LOGIN
    public ResponseEntity<?> login(String email, String password) {
        Seller seller = sellerRepository.findByEmail(email);

        if (seller == null) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Seller not found");
        }

        if (!seller.getPassword().equals(password)) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid email or password");
        }

        // Hide password before returning
        seller.setPassword(null);

        return ResponseEntity.ok(seller);
    }
}
