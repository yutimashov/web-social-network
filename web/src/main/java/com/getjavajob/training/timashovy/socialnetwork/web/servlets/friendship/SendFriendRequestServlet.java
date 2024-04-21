package com.getjavajob.training.timashovy.socialnetwork.web.servlets.friendship;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.AccountServiceImpl.getInstance;
import static java.lang.Long.valueOf;

public class SendFriendRequestServlet extends HttpServlet {

    private final AccountService accountService = getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long requesterAccountId = ((Account) req.getSession(false).getAttribute("account")).getId();
        accountService.addFriend(requesterAccountId, valueOf(req.getParameter("id")));
        resp.sendRedirect("/account?id=" + requesterAccountId);
    }

}
