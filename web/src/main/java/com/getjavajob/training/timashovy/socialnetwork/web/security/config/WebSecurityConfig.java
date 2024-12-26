package com.getjavajob.training.timashovy.socialnetwork.web.security.config;

import com.getjavajob.training.timashovy.socialnetwork.web.filters.AccountSessionFilter;
import com.getjavajob.training.timashovy.socialnetwork.web.filters.SetEncodingFilter;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.UrlStatusParameter.AUTH_DATA_ERROR;
import static org.slf4j.LoggerFactory.getLogger;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final UserDetailsService accountDetailsService;

    private final AuthenticationSuccessHandler successHandler;
    private static final Logger logger = getLogger(WebSecurityConfig.class);

    public WebSecurityConfig(UserDetailsService accountDetailsService, AuthenticationSuccessHandler successHandler) {
        this.accountDetailsService = accountDetailsService;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final int tokenValiditySeconds = 60 * 60 * 24;
        final String sessionCookieName = "JSESSIONID";
        final String loginPath = "/login";
        final String registerPath = "/register";
        logger.info("Came to securityFilterChain");
        return http
                .addFilterBefore(new SetEncodingFilter(), WebAsyncManagerIntegrationFilter.class)
                .addFilterAfter(new AccountSessionFilter(), RememberMeAuthenticationFilter.class)
                .authorizeHttpRequests(
                        registry -> {
                            logger.info("Configuring request matchers");
                            registry.requestMatchers(registerPath, loginPath).permitAll();
                            registry.anyRequest().authenticated();
                        }
                )
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    logger.info("Configuring form login");
                    httpSecurityFormLoginConfigurer
                            .loginPage(loginPath)
                            .successHandler(successHandler)
                            .failureUrl(loginPath + AUTH_DATA_ERROR.getValue())
                            .permitAll();
                })
                .logout(httpSecurityLogoutConfigurer -> {
                    logger.info("Configuring logout");
                    httpSecurityLogoutConfigurer
                            .deleteCookies(sessionCookieName)
                            .invalidateHttpSession(true);
                })
                .csrf(csrfConfigurer -> {
                    logger.info("Disabling CSRF protection");
                    csrfConfigurer.disable();
                })
                .rememberMe(httpSecurityRememberMeConfigurer -> {
                    logger.info("Configuring remember me");
                    httpSecurityRememberMeConfigurer
                            .userDetailsService(accountDetailsService)
                            .tokenValiditySeconds(tokenValiditySeconds);
                })
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
