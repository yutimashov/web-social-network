package com.getjavajob.authservice.web.config;

import com.getjavajob.authservice.service.account.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.security.AccountUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
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
        if (authentication.getPrincipal() instanceof AccountUserDetails) {
            String email = ((AccountUserDetails) authentication.getPrincipal()).getUsername();
            if (accountService.findByEmail(email).isPresent()) {
                Account account = accountService.findByEmail(email).get();
                HttpSession session = request.getSession();
                session.setAttribute("account", account);
                response.sendRedirect(generateRedirectBaseURL(request, account.getId()));
            }
        }
    }

    private String generateRedirectBaseURL(HttpServletRequest request, Long accountId) {
        // Получаем схему из заголовка, если прокси передает
        String scheme = request.getHeader("X-Forwarded-Proto");
        if (scheme == null || scheme.isEmpty()) {
            scheme = request.getScheme();
        }
        String host = request.getHeader("X-Forwarded-Host");
        if (host == null || host.isEmpty()) {
            host = request.getServerName();
        }
        String portHeader = request.getHeader("X-Forwarded-Port");
        int port;
        if (portHeader != null && !portHeader.isEmpty()) {
            port = Integer.parseInt(portHeader);
        } else {
            port = request.getServerPort();
        }
        String baseUrl = scheme + "://" + host;
        if (("http".equals(scheme) && port != 80) ||
                ("https".equals(scheme) && port != 443)) {
            baseUrl += ":" + port;
        }
        return baseUrl + "/account?id=" + accountId;
    }

}
