package com.getjavajob.training.timashovy.socialnetwork.web.servlets.auth;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.LoginService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.PasswordServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static java.util.Objects.isNull;
import static java.util.concurrent.TimeUnit.HOURS;

public class LoginServlet extends HttpServlet {

    private static final String LOGIN_COOKIE_NAME = "login";
    private static final String PASSWORD_COOKIE_NAME = "password";
    private static final int REMEMBER_ME_COOKIE_LIFETIME = (int) HOURS.toSeconds(1);
    private final LoginService loginService = LoginService.getInstance();
    private final PasswordService passwordService = PasswordServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(getJspPagePath("/auth/login")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Optional<Account> loggedInAccount = loginService.getLoggedInAccount(req.getParameter("email"),
                req.getParameter("password"));
        if (loggedInAccount.isPresent()) {
            HttpSession session = req.getSession();
            Account account = loggedInAccount.get();
            session.setAttribute("account", account);
            if (!isNull(req.getParameter("rememberMe"))) {
                createRememberMeCookies(account, resp);
            }
            resp.sendRedirect("/account?id=" + account.getId());
        } else {
            resp.sendRedirect("/login?error=auth-data");
        }
    }

    private void createRememberMeCookies(Account account, HttpServletResponse resp) {
        prepareCookie(resp, LOGIN_COOKIE_NAME, account.getEmail());
        if (passwordService.get(account.getId()).isPresent()) {
            prepareCookie(resp, PASSWORD_COOKIE_NAME, passwordService.get(account.getId()).get().getPassword());
        }
    }

    private void prepareCookie(HttpServletResponse resp, String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(REMEMBER_ME_COOKIE_LIFETIME);
        resp.addCookie(cookie);
    }

}
