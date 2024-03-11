package com.getjavajob.training.timashovy.socialnetwork.web.servlets;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.LoginService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl.getAccountServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.LoginService.getLoginServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;

public class LoginServlet extends HttpServlet {

    LoginService loginService = getLoginServiceInstance();
    AccountServiceImpl accountService = getAccountServiceInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(getJspPagePath("login")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userEnteredEmail = req.getParameter("email");
        String userEnteredPassword = req.getParameter("password");
        if (loginService.login(userEnteredEmail, userEnteredPassword)) {
            Password password = loginService.getUserPasswordByEmail(userEnteredEmail);
            Long accountId = password.getAccountId();
            Account account = accountService.getAccountById(accountId);
            req.getSession().setAttribute("account", account);
            req.getRequestDispatcher(getJspPagePath("/" + account.getId())).forward(req, resp);
        } else {
            resp.sendRedirect("/login?error&email=" + req.getParameter("email"));
        }
    }

}
