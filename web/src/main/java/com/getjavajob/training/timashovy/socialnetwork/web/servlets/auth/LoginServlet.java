package com.getjavajob.training.timashovy.socialnetwork.web.servlets.auth;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.LoginService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonRegistry;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.LOGIN_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.PASSWORD_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.SERVICE_SINGLETON_REGISTRY_ATTR;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.StatusTypes.AUTH_DATA_ERROR;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspPagePaths.LOGIN;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServletPaths.ACCOUNT_SERVLET_PATH;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServletPaths.LOGIN_SERVLET_PATH;
import static java.util.Objects.isNull;
import static java.util.concurrent.TimeUnit.HOURS;

public class LoginServlet extends HttpServlet {

    private final int rememberMeCookieLifetime = (int) HOURS.toSeconds(1);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(getJspPagePath(LOGIN)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LoginService loginService = ((ServiceSingletonRegistry) getServletContext()
                .getAttribute(SERVICE_SINGLETON_REGISTRY_ATTR)).getSingleton(LOGIN_SERVICE_SINGLETON);
        Optional<Account> loggedInAccount = loginService.getLoggedInAccount(req.getParameter("email"),
                req.getParameter("password")
        );
        if (loggedInAccount.isPresent()) {
            Account account = loggedInAccount.get();
            req.getSession().setAttribute("account", account);
            if (!isNull(req.getParameter("rememberMe"))) {
                createRememberMeCookies(account, resp);
            }
            resp.sendRedirect(ACCOUNT_SERVLET_PATH + "?id=" + account.getId());
        } else {
            resp.sendRedirect(LOGIN_SERVLET_PATH + AUTH_DATA_ERROR);
        }
    }

    private void createRememberMeCookies(Account account, HttpServletResponse resp) {
        prepareCookie(resp, "login", account.getEmail());
        PasswordService passwordService = ((ServiceSingletonRegistry) getServletContext()
                .getAttribute(SERVICE_SINGLETON_REGISTRY_ATTR)).getSingleton(PASSWORD_SERVICE_SINGLETON);
        if (passwordService.get(account.getId()).isPresent()) {
            prepareCookie(resp, "password", passwordService.get(account.getId()).get().getPassword());
        }
    }

    private void prepareCookie(HttpServletResponse resp, String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(rememberMeCookieLifetime);
        resp.addCookie(cookie);
    }

}
