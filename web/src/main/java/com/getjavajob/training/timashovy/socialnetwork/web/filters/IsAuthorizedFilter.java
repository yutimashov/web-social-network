package com.getjavajob.training.timashovy.socialnetwork.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServletPaths.*;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.StatusTypes.AUTHORIZATION_ERROR;
import static java.util.Objects.isNull;

public class IsAuthorizedFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String requestUri = req.getRequestURI();
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        if (requestUri.startsWith(STATIC_RESOURCES) || LOGIN_SERVLET_PATH.equals(requestUri)
                || REGISTRATION_SERVLET_PATH.equals(requestUri)) {
            filterChain.doFilter(req, resp);
        } else if (isNull(req.getSession().getAttribute("account"))) {
            resp.sendRedirect(LOGIN_SERVLET_PATH + AUTHORIZATION_ERROR);
        } else {
            filterChain.doFilter(req, resp);
        }
    }

}
