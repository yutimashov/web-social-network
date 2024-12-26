package com.getjavajob.training.timashovy.socialnetwork.web.security.config;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.security.AccountUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Set authenticated instance of Account to session.
 * Redirect client after successful authentication to home page.
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AccountService accountService;

    public CustomAuthenticationSuccessHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        if (authentication.getPrincipal() instanceof AccountUserDetails) {
            String email = ((AccountUserDetails) authentication.getPrincipal()).getUsername();
            if (accountService.findByEmail(email).isPresent()) {
                Account account = accountService.findByEmail(email).get();
                request.getSession().setAttribute("account", account);
                response.sendRedirect("/account?id=" + account.getId());
            }
        }
    }

}
