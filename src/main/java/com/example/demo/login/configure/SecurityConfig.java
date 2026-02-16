package com.example.demo.login.configure;

import com.example.demo.login.UserServiceForLogin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.context.SecurityContextHolderFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    UserServiceForLogin userDetailsService;

    public SecurityConfig(UserServiceForLogin userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return builder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/paybay/model/insert",
                                "/registrationApp",
                                "/forgetApp",
                                "/paybay/forget/password",
                                "/paybay/forget",
                                "/paybay/update/password",
                                "/login",
                                "/content/**",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()
                        // Accès réservé aux utilisateurs avec le rôle ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Accès réservé aux utilisateurs avec le rôle USER
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/loginApp?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .addFilterAfter(new NoCacheFilter(), SecurityContextHolderFilter.class)
                .headers(headers -> headers .cacheControl(withDefaults())); // active les headers anti-cache );


        return http.build();
    }
}


