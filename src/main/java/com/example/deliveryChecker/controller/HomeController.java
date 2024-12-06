package com.example.deliveryChecker.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            boolean isUser = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_USER"));
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

            model.addAttribute("isUser", isUser);
            model.addAttribute("isAdmin", isAdmin);
        }
        return "home";
    }
}
