package com.getjavajob.training.timashovy.socialnetwork.web.filters;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.LoginService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.LoginService.getLoginServiceInstance;
import static java.util.Objects.isNull;

public class RememberMeFilter implements Filter {

    private final LoginService loginService = getLoginServiceInstance();
    private static final String LOGIN_COOKIE_NAME = "login";
    private static final String PASSWORD_COOKIE_NAME = "password";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Cookie[] cookies = req.getCookies();
        Cookie emailCookie = findCookieByName(cookies, LOGIN_COOKIE_NAME);
        Cookie passwordCookie = findCookieByName(cookies, PASSWORD_COOKIE_NAME);
        if (emailCookie != null && passwordCookie != null) {
            String email = emailCookie.getValue();
            String password = passwordCookie.getValue();
            Account loggedInAccount = loginService.verifyLoginCredentials(email, password);
            if (loggedInAccount != null && req.getSession(false).getAttribute("account") == null) {
                req.getSession(false).setAttribute("account", loggedInAccount);
            }
        }
        filterChain.doFilter(req, resp);
    }

    private Cookie findCookieByName(Cookie[] cookies, String name) {
        if (!isNull(cookies) && !isNull(name)) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

}
