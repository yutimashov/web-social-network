package com.getjavajob.training.timashovy.socialnetwork.web.servlets.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountAvatarServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static java.lang.Long.valueOf;

public class AccountServlet extends HttpServlet {

    private final AccountService accountService = AccountServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long accountId = valueOf(req.getParameter("id"));
        if (accountService.getAccountById(accountId).isPresent()) {
            Account account = accountService.getAccountById(accountId).get();
            InputStream avatarInputStream = AccountAvatarServiceImpl.getInstance().get(accountId);
            req.setAttribute("account", account);
            req.setAttribute("avatarInputStream", avatarInputStream);
        }
        req.getRequestDispatcher(getJspPagePath("/account/account")).forward(req, resp);
    }

}
