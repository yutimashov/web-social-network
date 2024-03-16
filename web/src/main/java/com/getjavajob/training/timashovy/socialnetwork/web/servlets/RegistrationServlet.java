package com.getjavajob.training.timashovy.socialnetwork.web.servlets;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AvatarServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.PasswordServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.PhoneServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl.getAccountServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AvatarServiceImpl.getAvatarService;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.PasswordServiceImpl.getPasswordServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.PhoneServiceImpl.getPhoneServiceImpl;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.CredentialHashUtil.generateSalt;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;

public class RegistrationServlet extends HttpServlet {

    AccountServiceImpl accountService = getAccountServiceInstance();
    PasswordServiceImpl passwordService = getPasswordServiceInstance();
    AvatarServiceImpl avatarService = getAvatarService();
    PhoneServiceImpl phoneService = getPhoneServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(getJspPagePath("register")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account registeringAccount = createAccount(req);
        accountService.createAccount(registeringAccount);
        avatarService.uploadAvatar(registeringAccount.getId(), req.getPart("avatar").getInputStream());
        passwordService.create(new Password(registeringAccount.getId(), req.getParameter("password"), generateSalt()));
        phoneService.createPhone(registeringAccount, req.getParameter("personalPhoneNumber"), PhoneType.PERSONAL);
        phoneService.createPhone(registeringAccount, req.getParameter("workPhoneNumber"), PhoneType.WORKING);
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
                .birthDate(LocalDate.parse(req.getParameter("birthDate")))
                .build();
    }

}
