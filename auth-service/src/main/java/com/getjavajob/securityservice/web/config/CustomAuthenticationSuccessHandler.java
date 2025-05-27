package com.getjavajob.securityservice.web.config;

import com.getjavajob.securityservice.service.account.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.security.AccountUserDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Set authenticated instance of Account to session.
 * Redirect client after successful authentication to home page.
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = getLogger(CustomAuthenticationSuccessHandler.class);
    private final AccountService accountService;

    public CustomAuthenticationSuccessHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Add authenticated account to session and redirect account to home page.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     *                       the authentication process.
     * @throws IOException when cannot form response for sending redirect
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        logger.info("Authentication object: {}", authentication);
        // 2. Получаем SecurityContext из SecurityContextHolder
        SecurityContext context = SecurityContextHolder.getContext();
        logger.info("SecurityContext class: {}", context.getClass().getName());
        if (context.getAuthentication() != null) {
            logger.info("SecurityContext contains Authentication: {}", context.getAuthentication().getClass().getName());
            logger.info("Is authenticated: {}", context.getAuthentication().isAuthenticated());
            logger.info("Principal: {}", context.getAuthentication().getPrincipal());
            logger.info("Authorities: {}", context.getAuthentication().getAuthorities());
        } else {
            logger.warn("SecurityContext does NOT contain an Authentication");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof AccountUserDetails) {
            AccountUserDetails userDetails = (AccountUserDetails) principal;
            logger.info("UserDetails username: {}", userDetails.getUsername());
            logger.info("UserDetails role: {}", userDetails.getAuthorities());
        }
        if (authentication.getPrincipal() instanceof AccountUserDetails) {
            String email = ((AccountUserDetails) authentication.getPrincipal()).getUsername();
            if (accountService.findByEmail(email).isPresent()) {
                Account account = accountService.findByEmail(email).get();
                HttpSession session = request.getSession();
                session.setAttribute("account", account);
                logger.info("сервис работает на: {}", request.getHeader("Host"));
                logger.info("изначально запрос пришел от: {}", request.getHeader("X-Forwarded-Host"));
                String scheme = request.getScheme(); // http
                String serverName = request.getHeader("X-Forwarded-Host"); // localhost:8089
                if (serverName == null || serverName.isEmpty()) {
                    serverName = request.getServerName(); // fallback
                }
                int port = request.getServerPort();
                String baseUrl = scheme + "://" + serverName;
                logger.info("path for redirect: {}", baseUrl + "/account?id=" + account.getId());
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        logger.info("Cookie in request: {} = {}", cookie.getName(), cookie.getValue());
                    }
                } else {
                    logger.warn("No cookies found in the request.");
                }
                response.sendRedirect(baseUrl + "/account?id=" + account.getId());
            }
        }
    }

}
