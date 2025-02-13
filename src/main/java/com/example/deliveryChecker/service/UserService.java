package com.example.deliveryChecker.service;

import com.example.deliveryChecker.controller.UserController;
import com.example.deliveryChecker.model.User;
import com.example.deliveryChecker.repository.UserRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Создание нового пользователя
    public boolean createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return false; // Пользователь с таким email уже существует
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        log.info("user: {}", user.isActive());
        userRepository.save(user);
        return true;
    }

    // Поиск пользователя по email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}