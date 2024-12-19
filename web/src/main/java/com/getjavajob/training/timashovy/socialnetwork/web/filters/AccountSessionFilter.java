package com.getjavajob.training.timashovy.socialnetwork.web.filters;

import com.getjavajob.training.timashovy.socialnetwork.web.security.config.service.AccountUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.isNull;

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
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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
