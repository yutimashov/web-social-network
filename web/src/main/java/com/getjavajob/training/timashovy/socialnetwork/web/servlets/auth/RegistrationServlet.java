package com.getjavajob.training.timashovy.socialnetwork.web.servlets.auth;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.ImageService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.PhoneServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.WORKING;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl.getAccountServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountAvatarServiceImpl.getAccountAvatarServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.PasswordServiceImpl.getPasswordServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.PhoneServiceImpl.getPhoneServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;

public class RegistrationServlet extends HttpServlet {

    private final AccountService accountService = getAccountServiceInstance();
    private final PasswordService passwordService = getPasswordServiceInstance();
    private final ImageService avatarService = getAccountAvatarServiceInstance();
    private final PhoneServiceImpl phoneService = getPhoneServiceInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(getJspPagePath("register")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        registerAccount(createAccount(req), req);
        resp.sendRedirect("/login");
    }

    private Account createAccount(HttpServletRequest req) {
        return new Account.Builder()
                .firstName(req.getParameter("name"))
                .lastName(req.getParameter("lastName"))
                .middleName(req.getParameter("middleName"))
                .email(req.getParameter("email"))
                .skype(req.getParameter("skype"))
                .icq(req.getParameter("icq"))
                .build();
    }

    private void registerAccount(Account account, HttpServletRequest req) throws ServletException, IOException {
        accountService.createAccount(account);
        Long accountId = account.getId();
        phoneService.createPhone(accountId, req.getParameter("personalPhoneNumber"), PERSONAL);
        phoneService.createPhone(accountId, req.getParameter("workPhoneNumber"), WORKING);
        avatarService.upload(accountId, req.getPart("avatar").getInputStream());
        passwordService.create(accountId, req.getParameter("password"));
    }

}
