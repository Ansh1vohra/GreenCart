package com.greencart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.greencart.model.Seller;
import com.greencart.service.SellerService;

@Controller
@RequestMapping("/sellers")
public class SellerViewController {

    @Autowired
    private SellerService sellerService;

    // 🟢 Show Signup Form
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("seller", new Seller());
        return "seller_signup";
    }

    // 🟢 Process Signup
    @PostMapping("/signup")
    public String processSignup(@ModelAttribute Seller seller, RedirectAttributes redirectAttributes) {
        try {
            sellerService.signup(seller);
            redirectAttributes.addFlashAttribute("message", "Signup successful! Please log in.");
            return "redirect:/sellers/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Signup failed: " + e.getMessage());
            return "redirect:/sellers/signup";
        }
    }

    // 🟢 Show Login Form
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("seller", new Seller());
        return "seller_login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute Seller seller, RedirectAttributes redirectAttributes) {
        ResponseEntity<?> response = sellerService.login(seller.getEmail(), seller.getPassword());

        if (response.getStatusCode().is2xxSuccessful()) {
            redirectAttributes.addFlashAttribute("message", "Login successful!");
            return "redirect:/sellers/dashboard"; // redirect to seller dashboard or product list
        } else {
            redirectAttributes.addFlashAttribute("message", response.getBody().toString());
            return "redirect:/sellers/login";
        }
    }

    // 🟩 Show Dashboard
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "seller_dashboard"; // => src/main/resources/templates/seller_dashboard.html
    }

    // 🟩 Add Product Page
    @GetMapping("/add-product")
    public String showAddProductPage() {
        return "seller_add_product"; // => templates/seller_add_product.html
    }

    // 🟩 View My Products Page
    @GetMapping("/products")
    public String showSellerProductsPage() {
        return "sellers_product"; // => templates/seller_products.html
    }

    // 🟩 View Orders Page (you can make later)
    @GetMapping("/orders")
    public String showOrdersPage() {
        return "seller_orders"; // => templates/seller_orders.html
    }

}
