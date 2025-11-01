package com.greencart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.greencart.model.Seller;
import com.greencart.service.SellerService;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Seller seller) {
        return sellerService.signup(seller);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Seller loginRequest) {
        return sellerService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }
}
