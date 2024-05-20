package com.getjavajob.training.timashovy.socialnetwork.web.servlets.auth;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.PhoneServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.WORKING;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.ServiceSingletonRegistry.getServiceSingletonRegistry;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.ServiceSingletonsNames.*;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.ErrorTypes.ACCOUNT_REGISTRATION_ERROR;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspPagePaths.REGISTER_PAGE;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServletPaths.LOGIN_SERVLET_PATH;

public class RegisterServlet extends HttpServlet {

    private final AccountService accountService = getServiceSingletonRegistry().getSingleton(ACCOUNT_SERVICE_SINGLETON);
    private final PasswordService passwordService = getServiceSingletonRegistry().getSingleton(PASSWORD_SERVICE_SINGLETON);
    private final PhoneServiceImpl phoneService = getServiceSingletonRegistry().getSingleton(PHONE_SERVICE_SINGLETON);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(getJspPagePath(REGISTER_PAGE)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            registerAccount(req);
            resp.sendRedirect(LOGIN_SERVLET_PATH);
        } catch (Exception e) {
            resp.sendRedirect(LOGIN_SERVLET_PATH + ACCOUNT_REGISTRATION_ERROR);
        }
    }

    private void registerAccount(HttpServletRequest req) throws ServletException, IOException {
        Long accountId = accountService.create(new Account.Builder()
                .firstName(req.getParameter("name"))
                .lastName(req.getParameter("lastName"))
                .middleName(req.getParameter("middleName"))
                .email(req.getParameter("email"))
                .skype(req.getParameter("skype"))
                .icq(req.getParameter("icq"))
                .avatar(req.getPart("avatar").getInputStream())
                .build());
        phoneService.createPhone(accountId, req.getParameter("personalPhoneNumber"), PERSONAL);
        phoneService.createPhone(accountId, req.getParameter("workPhoneNumber"), WORKING);
        passwordService.create(accountId, req.getParameter("password"));
    }

}
