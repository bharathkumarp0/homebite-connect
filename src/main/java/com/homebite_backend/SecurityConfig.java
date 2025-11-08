package com.homebite_backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //  Allow public access to login, signup, static assets, etc.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/signup", "/adduser", "/loginUser", "/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().permitAll()  // allow everything for testing
                )

                //  Disable Spring Security's built-in login form
                .formLogin(form -> form.disable())

                //  Disable logout intercepts for now
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                //  Disable CSRF to avoid 403 POST errors during manual login
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    //  Password encoder bean (required for @Autowired PasswordEncoder)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
