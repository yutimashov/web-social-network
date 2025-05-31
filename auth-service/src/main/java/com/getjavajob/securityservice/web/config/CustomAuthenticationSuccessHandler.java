package com.getjavajob.securityservice.web.config;

import com.getjavajob.securityservice.service.account.AccountService;
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
        String scheme = request.getScheme();
        String serverName = request.getHeader("X-Forwarded-Host");
        if (serverName == null || serverName.isEmpty()) {
            serverName = request.getServerName();
        }
        return scheme + "://" + serverName + "/account?id=" + accountId;
    }

}
