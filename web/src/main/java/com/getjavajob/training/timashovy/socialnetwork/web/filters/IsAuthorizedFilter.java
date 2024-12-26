package com.getjavajob.training.timashovy.socialnetwork.web.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.UrlStatusParameter.AUTHORIZATION_ERROR;
import static java.util.Objects.isNull;

public class IsAuthorizedFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String requestUri = req.getRequestURI();
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        if (requestUri.startsWith("/static") || "/login".equals(requestUri)
                || "/register".equals(requestUri)) {
            filterChain.doFilter(req, resp);
        } else if (isNull(req.getSession().getAttribute("account"))) {
            resp.sendRedirect("/login" + AUTHORIZATION_ERROR.getValue());
        } else {
            filterChain.doFilter(req, resp);
        }
    }

}
