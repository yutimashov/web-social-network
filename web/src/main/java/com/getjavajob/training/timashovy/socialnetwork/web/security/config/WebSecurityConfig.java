package com.getjavajob.training.timashovy.socialnetwork.web.security.config;

import com.getjavajob.training.timashovy.socialnetwork.web.filters.AccountSessionFilter;
import com.getjavajob.training.timashovy.socialnetwork.web.filters.SetEncodingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.UrlStatusParameter.AUTH_DATA_ERROR;

/**
 * Configuration for Spring Security module
 */
@Configuration
public class WebSecurityConfig {

    private final UserDetailsService accountDetailsService;

    private final AuthenticationSuccessHandler successHandler;

    @Value("${security.token-validity-duration}")
    private int tokenValidityDurationSec;

    @Value("${security.session-cookie-name}")
    private String sessionCookieName;

    @Value("${security.login-url}")
    private String loginUrl;

    @Value("${security.login-page}")
    private String loginPage;

    @Value("${security.register-url}")
    private String registerUrl;

    @Value("${security.register-page}")
    private String registerPage;

    public WebSecurityConfig(UserDetailsService accountDetailsService, AuthenticationSuccessHandler successHandler) {
        this.accountDetailsService = accountDetailsService;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .addFilterBefore(new SetEncodingFilter(), WebAsyncManagerIntegrationFilter.class)
                .addFilterAfter(new AccountSessionFilter(), RememberMeAuthenticationFilter.class)
                .authorizeHttpRequests(
                        registry -> {
                            registry.requestMatchers(loginUrl, registerUrl).permitAll();
                            registry.requestMatchers(loginPage, registerPage).permitAll();
                            registry.anyRequest().authenticated();
                        }
                )
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .loginPage(loginUrl)
                        .successHandler(successHandler)
                        .failureUrl(loginUrl + AUTH_DATA_ERROR.getValue())
                        .permitAll())
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .deleteCookies(sessionCookieName)
                        .invalidateHttpSession(true))
                .csrf(AbstractHttpConfigurer::disable)
                .rememberMe(httpSecurityRememberMeConfigurer -> httpSecurityRememberMeConfigurer
                        .userDetailsService(accountDetailsService)
                        .tokenValiditySeconds(tokenValidityDurationSec))
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return accountDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowBackSlash(true);
        firewall.setAllowUrlEncodedDoubleSlash(true);
        return (web) -> web.httpFirewall(firewall);
    }

}
