package com.greencart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greencart.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
