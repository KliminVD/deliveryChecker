package com.example.deliveryChecker.service;

import com.example.deliveryChecker.model.User;
import com.example.deliveryChecker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

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
        userRepository.save(user);
        return true;
    }

    // Поиск пользователя по email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
