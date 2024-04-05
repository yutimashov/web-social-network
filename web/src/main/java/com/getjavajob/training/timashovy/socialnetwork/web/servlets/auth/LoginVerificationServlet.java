package com.getjavajob.training.timashovy.socialnetwork.web.servlets.auth;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.LoginService;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.LoginService.getLoginServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.PasswordServiceImpl.getPasswordServiceInstance;
import static java.util.Objects.isNull;

public class LoginVerificationServlet extends HttpServlet {

    private final LoginService loginService = getLoginServiceInstance();
    private final PasswordService passwordService = getPasswordServiceInstance();
    private static final String LOGIN_COOKIE_NAME = "login";
    private static final String PASSWORD_COOKIE_NAME = "password";
    private static final int LOGIN_CREDENTIAL_COOKIES_LIFETIME = 3600;

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
        Cookie loginCookie = new Cookie(LOGIN_COOKIE_NAME, account.getEmail());
        loginCookie.setMaxAge(LOGIN_CREDENTIAL_COOKIES_LIFETIME);
        Cookie passwordCookie = new Cookie(PASSWORD_COOKIE_NAME, passwordService.get(account).getPassword());
        passwordCookie.setMaxAge(LOGIN_CREDENTIAL_COOKIES_LIFETIME);
        resp.addCookie(loginCookie);
        resp.addCookie(passwordCookie);
    }

}
