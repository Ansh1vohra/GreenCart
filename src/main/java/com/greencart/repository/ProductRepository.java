package com.greencart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greencart.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySellerId(Long sellerId);
    List<Product> findByLocation(String location); // optional, for future use
}
