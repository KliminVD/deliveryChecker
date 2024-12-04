package com.example.deliveryChecker.controller;

import com.example.deliveryChecker.model.Parcel;
import com.example.deliveryChecker.model.User;
import com.example.deliveryChecker.service.ParcelService;
import com.example.deliveryChecker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final ParcelService parcelService;

    public UserController(UserService userService, ParcelService parcelService) {
        this.userService = userService;
        this.parcelService = parcelService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registration() {
        return "register";
    }

    @PostMapping("/register")
    public String createUser(@RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String role,
                             Model model) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        if (userService.createUser(user)) {
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Registration failed. Please try again.");
            return "register";
        }
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/parcels")
    public String userParcels(Model model, @AuthenticationPrincipal User user) {
        List<Parcel> parcels = parcelService.findAllByUserEmail(user.getEmail());
        model.addAttribute("parcels", parcels);
        return "user/parcels";
    }
}