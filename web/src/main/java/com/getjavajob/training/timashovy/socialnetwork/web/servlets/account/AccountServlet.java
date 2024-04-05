package com.getjavajob.training.timashovy.socialnetwork.web.servlets.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl.getAccountServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AvatarServiceImpl.getAvatarServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static java.lang.Long.valueOf;

public class AccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long accountId = valueOf(req.getParameter("id"));
        if (getAccountServiceInstance().getAccountById(accountId).isPresent()) {
            Account account = getAccountServiceInstance().getAccountById(accountId).get();
            InputStream avatarInputStream = getAvatarServiceInstance().get(accountId);
            req.setAttribute("account", account);
            req.setAttribute("avatarInputStream", avatarInputStream);
        }
        req.getRequestDispatcher(getJspPagePath("account")).forward(req, resp);
    }

}
