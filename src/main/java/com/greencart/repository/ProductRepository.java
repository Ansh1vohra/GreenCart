package com.greencart.repository;

import com.greencart.model.Product;
import com.greencart.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySeller(Seller seller);
}
