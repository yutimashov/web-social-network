package com.getjavajob.training.timashovy.socialnetwork.web.servlets;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.AuthToken;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AuthTokenServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.LoginService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AuthTokenServiceImpl.getAuthTokenServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.LoginService.getLoginServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.CredentialHashUtil.generateSalt;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.CredentialHashUtil.hashCredential;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static java.util.Objects.isNull;

public class LoginServlet extends HttpServlet {

    private final LoginService loginService = getLoginServiceInstance();
    private final AuthTokenServiceImpl authTokenService = getAuthTokenServiceInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(getJspPagePath("login")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userEnteredEmail = req.getParameter("email");
        String userEnteredPassword = req.getParameter("password");
        Account account = loginService.checkLogin(userEnteredEmail, userEnteredPassword);
        boolean rememberMe = "true".equals(req.getParameter("rememberMe"));
        if (!isNull(account)) {
            HttpSession session = req.getSession();
            session.setAttribute("account", account);
            if (rememberMe) {
                String salt = generateSalt();
                AuthToken authToken = new AuthToken(salt, hashCredential(generateSalt(), salt), account.getId());
                authTokenService.create(authToken);
                Cookie tokenCookie = new Cookie("token", authToken.getToken());
                tokenCookie.setMaxAge(30);
                Cookie validatorCookie = new Cookie("validator", authToken.getValidator());
                validatorCookie.setMaxAge(30);
                resp.addCookie(tokenCookie);
                resp.addCookie(validatorCookie);
            }
            resp.sendRedirect("/account?id=" + account.getId());
        } else {
            resp.sendRedirect("/login?error");
        }
    }

}
