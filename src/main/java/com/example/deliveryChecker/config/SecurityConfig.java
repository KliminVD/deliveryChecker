package com.example.deliveryChecker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/trackParcel", "/login", "/register", "/", "/home").permitAll()  // Доступность страниц без аутентификации
                                .anyRequest().authenticated()  // Все остальные запросы требуют аутентификации
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")  // Указываем кастомную страницу логина
                                .permitAll()  // Разрешаем доступ всем пользователям на страницу логина
                )
                .logout(LogoutConfigurer::permitAll  // Разрешаем выход всем пользователям
                );

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}