package com.greencart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.greencart.model.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findByEmail(String email);
}
