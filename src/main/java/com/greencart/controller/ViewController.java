package com.greencart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    // @GetMapping("/login")
    // public String login() {
    //     return "login";
    // }
    // @GetMapping("/signup")
    // public String signup() {
    //     return "signup";
    // }
    // @GetMapping("/seller-login")
    // public String sellerLogin() {
    //     return "seller_login";
    // }
    // @GetMapping("/seller-signup")
    // public String sellerSignup() {  
    //     return "seller_signup";
    // }
    @GetMapping("/cart")
    public String cartPage() {
        return "cart";  // corresponds to cart.html
    }

}
