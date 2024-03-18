package com.getjavajob.training.timashovy.socialnetwork.web.filters;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.LoginService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RememberMeFilter implements Filter {

    private final LoginService loginService = LoginService.getLoginServiceInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Cookie[] cookies = req.getCookies();
        Cookie emailCookie = findCookieByName(cookies, "email");
        Cookie passwordCookie = findCookieByName(cookies, "password");
        if (emailCookie != null && passwordCookie != null) {
            String email = emailCookie.getValue();
            String password = passwordCookie.getValue();
            Account loggedInAccount = loginService.checkLogin(email, password);
            if (loggedInAccount != null) {
                req.getSession().setAttribute("account", loggedInAccount);
            }
        }
        filterChain.doFilter(req, resp);
    }

    private Cookie findCookieByName(Cookie[] cookies, String name) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

}
