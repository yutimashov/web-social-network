package com.getjavajob.training.timashovy.socialnetwork.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

public class IsAuthorizedFilter implements Filter {

    private static final String LOGIN = "/login";
    private static final String REGISTER = "/register";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String requestUri = req.getRequestURI();
        if (requestUri.equals(LOGIN) || requestUri.equals(REGISTER)) {
            filterChain.doFilter(req, resp);
        } else if (isNull(req.getSession(false)) || isNull(req.getSession(false).getAttribute("account"))) {
            resp.sendRedirect("/login?error=authorization");
        } else {
            filterChain.doFilter(req, resp);
        }
    }

}
