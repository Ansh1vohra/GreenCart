package com.greencart.controller;

import com.greencart.model.Order;
import com.greencart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int quantity) {
        Order order = orderService.createOrder(userId, productId, quantity);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    // ✅ New endpoint: Seller marks order as completed using OTP
    @PutMapping("/complete")
    public ResponseEntity<?> completeOrder(
            @RequestParam Long orderId,
            @RequestParam String orderCode) {

        try {
            Order updatedOrder = orderService.completeOrder(orderId, orderCode);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
