package com.greencart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greencart.model.User;
import com.greencart.service.UserService;

@Controller
public class LoginViewController {

    @Autowired
    private UserService userService;

    // ✅ Show login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // ✅ Handle login form POST
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {
        var response = userService.login(email, password);
        if (response.getStatusCode().is2xxSuccessful()) {
            // success → redirect to product list
            return "redirect:/products";
        } else {
            model.addAttribute("error", "Invalid credentials!");
            return "login";
        }
    }

    // ✅ Show signup page
    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // ✅ Handle signup form POST
    @PostMapping("/signup")
    public String signup(@ModelAttribute User user, Model model) {
        var response = userService.signup(user);
        if (response.getStatusCode().is2xxSuccessful()) {
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Signup failed, try again!");
            return "signup";
        }
    }
}
