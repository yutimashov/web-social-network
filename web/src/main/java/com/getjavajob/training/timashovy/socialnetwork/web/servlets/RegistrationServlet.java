package com.getjavajob.training.timashovy.socialnetwork.web.servlets;

import com.getjavajob.training.timashovy.socialnetwork.common.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.PasswordServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl.getAccountServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.PasswordServiceImpl.getPasswordServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;

public class RegistrationServlet extends HttpServlet {

    AccountServiceImpl accountService = getAccountServiceInstance();
    PasswordServiceImpl passwordService = getPasswordServiceInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(getJspPagePath("register")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        Account account = new Account.Builder()
                .firstName(req.getParameter("name"))
                .lastName(req.getParameter("lastName"))
                .middleName(req.getParameter("middleName"))
                .email(req.getParameter("email"))
                .skype(req.getParameter("skype"))
                .icq(req.getParameter("icq"))
                .personalPhoneNumber(req.getParameter("personalPhoneNumber"))
                .workPhoneNumber(req.getParameter("workPhoneNumber"))
                .birthDate(LocalDate.parse(req.getParameter("birthDate")))
                .build();
        accountService.createAccount(account);
        passwordService.savePassword(account, req.getParameter("password"));
        resp.sendRedirect("/login");
    }

}
