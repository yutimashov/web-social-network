package com.getjavajob.training.timashovy.socialnetwork.web.servlets.auth;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.ImageService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.AccountAvatarServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.AccountServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.PasswordServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.PhoneServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.WORKING;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;

public class RegisterServlet extends HttpServlet {

    private final AccountService accountService = AccountServiceImpl.getInstance();
    private final PasswordService passwordService = PasswordServiceImpl.getInstance();
    private final ImageService avatarService = AccountAvatarServiceImpl.getInstance();
    private final PhoneServiceImpl phoneService = PhoneServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(getJspPagePath("/auth/register")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            registerAccount(createAccount(req), req);
            resp.sendRedirect("/login?reg=success");
        } catch (Exception e) {
            resp.sendRedirect("/login?reg=fail");
        }
    }

    private void registerAccount(Account account, HttpServletRequest req) throws ServletException, IOException {
        accountService.createAccount(account);
        Long accountId = account.getId();
        phoneService.createPhone(accountId, req.getParameter("personalPhoneNumber"), PERSONAL);
        phoneService.createPhone(accountId, req.getParameter("workPhoneNumber"), WORKING);
        avatarService.create(accountId, req.getPart("avatar").getInputStream());
        passwordService.create(accountId, req.getParameter("password"));
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

}
