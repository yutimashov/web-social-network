package com.getjavajob.training.timashovy.socialnetwork.web.filters;

import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.security.AccountUserDetails;
import org.springframework.security.core.Authentication;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.isNull;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

/**
 * Filter check storing authenticated Account object when Account has been authenticated.
 * If sessionId cookie expired or deleted, there should be Account object in session when client
 * has authenticated using remember-me cookie.
 */
public class AccountSessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        if (isNull(session.getAttribute("account"))) {
            Authentication authentication = getContext().getAuthentication();
            if (!isNull(authentication) && authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof AccountUserDetails) {
                    session.setAttribute("account", ((AccountUserDetails) principal).getAccount());
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
