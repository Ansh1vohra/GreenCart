package com.greencart.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greencart.model.Order;
import com.greencart.model.OrderItem;
import com.greencart.model.Product;
import com.greencart.model.User;
import com.greencart.repository.OrderItemRepository;
import com.greencart.repository.OrderRepository;
import com.greencart.repository.ProductRepository;
import com.greencart.repository.UserRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Create order with multiple products
    public Order createOrder(Long userId, List<Map<String, Object>> items, String address) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setDeliveryAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Pending");
        order.setOrderCode(String.valueOf((int) (Math.random() * 900000) + 100000));

        double totalAmount = 0;
        double totalCarbon = 0;

        List<OrderItem> orderItems = new ArrayList<>();

        // ✅ Process each item
        for (Map<String, Object> itemData : items) {
            Long productId = ((Number) itemData.get("productId")).longValue();
            int quantity = ((Number) itemData.get("quantity")).intValue();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < quantity) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // reduce stock
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);

            // calculate per-item
            double subtotal = product.getPrice() * quantity;
            totalAmount += subtotal;

            double carbon = quantity * 0.5;
            totalCarbon += carbon;

            // create item
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        totalCarbon = Math.round(totalCarbon * 100.0) / 100.0;

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setCarbonFootprint(totalCarbon);

        // save order & items (cascade)
        Order savedOrder = orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        return savedOrder;
    }

    // ✅ Fetch all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // ✅ Fetch orders by user
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // ✅ Complete order (using OTP)
    public Order completeOrder(Long orderId, String orderCode) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getOrderCode().equals(orderCode)) {
            throw new RuntimeException("Invalid order code. Cannot mark as complete.");
        }

        if ("Completed".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("Order already completed.");
        }

        order.setStatus("Completed");
        return orderRepository.save(order);
    }
}
