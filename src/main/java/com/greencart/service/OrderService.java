package com.greencart.service;

import com.greencart.model.Order;
import com.greencart.model.Product;
import com.greencart.model.User;
import com.greencart.repository.OrderRepository;
import com.greencart.repository.ProductRepository;
import com.greencart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Order createOrder(Long userId, Long productId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // ✅ Check stock availability
        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }

        // ✅ Reduce stock
        int updatedStock = product.getStock() - quantity;
        product.setStock(updatedStock);
        productRepository.save(product);  // save updated stock

        // ✅ Calculate totals
        double total = product.getPrice() * quantity;
        double carbonFootprint = quantity * 0.5;
        carbonFootprint = Math.round(carbonFootprint * 100.0) / 100.0;

        String orderCode = String.valueOf((int) (Math.random() * 900000) + 100000);

        // ✅ Create new order
        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setTotalAmount(total);
        order.setCarbonFootprint(carbonFootprint);
        order.setOrderCode(orderCode);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Pending");

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order completeOrder(Long orderId, String orderCode) {
    Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

    // Validate order code (OTP)
    if (!order.getOrderCode().equals(orderCode)) {
        throw new RuntimeException("Invalid order code. Cannot mark as complete.");
    }

    // Check status
    if ("Completed".equalsIgnoreCase(order.getStatus())) {
        throw new RuntimeException("Order already completed.");
    }

    // Update status
    order.setStatus("Completed");

    return orderRepository.save(order);
}

}
