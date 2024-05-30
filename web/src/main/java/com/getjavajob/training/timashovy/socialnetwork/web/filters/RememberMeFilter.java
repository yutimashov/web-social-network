package com.getjavajob.training.timashovy.socialnetwork.web.filters;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.LoginServiceImpl;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonRegistry.getInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.LOGIN_SERVICE_SINGLETON;
import static java.util.Objects.isNull;

public class RememberMeFilter implements Filter {

    private static final String LOGIN_COOKIE_NAME = "login";
    private static final String PASSWORD_COOKIE_NAME = "password";
    private final LoginServiceImpl loginService = getInstance().getSingleton(LOGIN_SERVICE_SINGLETON);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Cookie[] cookies = req.getCookies();
        Cookie emailCookie = findCookieByName(cookies, LOGIN_COOKIE_NAME);
        Cookie passwordCookie = findCookieByName(cookies, PASSWORD_COOKIE_NAME);
        if (!isNull(emailCookie) && !isNull(passwordCookie)) {
            Optional<Account> loggedInAccount = loginService.getLoggedInAccount(emailCookie.getValue(),
                    passwordCookie.getValue());
            if (isNull(req.getSession(false)) && loggedInAccount.isPresent()) {
                req.getSession().setAttribute("account", loggedInAccount);
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
