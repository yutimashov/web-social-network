package com.getjavajob.training.timashovy.socialnetwork.web.filters;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.LoginService;
import org.springframework.context.ApplicationContext;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.LOGIN_SERVICE_BEAN;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.APPLICATION_CONTEXT;
import static java.util.Objects.isNull;

public class RememberMeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Cookie[] cookies = req.getCookies();
        Cookie emailCookie = findCookieByName(cookies, "login");
        Cookie passwordCookie = findCookieByName(cookies, "password");
        if (!isNull(emailCookie) && !isNull(passwordCookie)) {
            LoginService loginService = (LoginService) ((ApplicationContext) req.getServletContext()
                    .getAttribute(APPLICATION_CONTEXT)).getBean(LOGIN_SERVICE_BEAN);
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
