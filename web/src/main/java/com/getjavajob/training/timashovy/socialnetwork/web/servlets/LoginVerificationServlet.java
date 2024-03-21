package com.getjavajob.training.timashovy.socialnetwork.web.servlets;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.LoginService;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.LoginService.getLoginServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.PasswordServiceImpl.getPasswordServiceInstance;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

public class LoginVerificationServlet extends HttpServlet {

    private final LoginService loginService = getLoginServiceInstance();
    private final PasswordService passwordService = getPasswordServiceInstance();
    private static final String LOGIN_COOKIE_NAME = "login";
    private static final String PASSWORD_COOKIE_NAME = "password";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Optional<Account> loggedInAccount = getLoggedInAccount(req);
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

    private Optional<Account> getLoggedInAccount(HttpServletRequest req) {
        String enteredEmail = req.getParameter("email");
        String enteredPassword = req.getParameter("password");
        if (isNull(enteredEmail) || isNull(enteredPassword)) {
            return empty();
        }
        return ofNullable(loginService.verifyRawLoginCredentials(enteredEmail, enteredPassword));
    }

    private void createRememberMeCookies(Account account, HttpServletResponse resp) {
        Cookie loginCookie = new Cookie(LOGIN_COOKIE_NAME, account.getEmail());
        loginCookie.setMaxAge(3600);
        Cookie passwordCookie = new Cookie(PASSWORD_COOKIE_NAME, passwordService.get(account).getPassword());
        passwordCookie.setMaxAge(3600);
        resp.addCookie(loginCookie);
        resp.addCookie(passwordCookie);
    }

}
