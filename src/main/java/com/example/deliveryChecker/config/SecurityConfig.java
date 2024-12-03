package com.example.deliveryChecker.config;

import com.example.deliveryChecker.service.CustomUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    private final CustomUserDetailService customUserDetailsService;

    public SecurityConfig(CustomUserDetailService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/trackParcel", "/login", "/register", "/", "/home", "/access_denied").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN") // Ограничения для администратора
                                .requestMatchers("/user/**").hasRole("USER") // Ограничения для пользователей
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .usernameParameter("email")
                                .permitAll()
                                .successHandler(authenticationSuccessHandler()) // Настраиваем перенаправление
                )
                .logout(LogoutConfigurer::permitAll)
                .userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            log.info("role: {}", role);
            if ("ROLE_ADMIN".equals(role)) {
                response.sendRedirect("/admin/parcels");
            } else if ("ROLE_USER".equals(role)) {
                response.sendRedirect("/user/parcels");
            } else {
                response.sendRedirect("/"); // На случай неопределенной роли
            }
        };
    }
}
