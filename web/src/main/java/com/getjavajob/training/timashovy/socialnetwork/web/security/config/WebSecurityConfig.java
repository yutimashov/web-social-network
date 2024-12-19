package com.getjavajob.training.timashovy.socialnetwork.web.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig {

    private final UserDetailsService accountDetailsService;

    private final AuthenticationSuccessHandler successHandler;

    public WebSecurityConfig(UserDetailsService accountDetailsService, AuthenticationSuccessHandler successHandler) {
        this.accountDetailsService = accountDetailsService;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        registry -> {
                            registry.requestMatchers("/account/all", "/register").permitAll();
                            registry.requestMatchers("/group/**").hasRole("ADMIN");
                            registry.requestMatchers("/account/**").hasRole("REGULAR");
                            registry.anyRequest().authenticated();
                        }
                )
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer
                            .loginPage("/login")
                            .successHandler(successHandler)
                            .permitAll();
                })
                .logout(LogoutConfigurer::permitAll)
                .csrf().disable()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return accountDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
