package com.getjavajob.training.timashovy.socialnetwork.web.servlets.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl.getInstance;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;

public class OutgoingFriendRequestServlet extends HttpServlet {

    private final AccountService accountService = getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long accountId = ((Account) req.getSession().getAttribute("account")).getId();
        req.setAttribute("outgoingFriendRequests", accountService.getOutgoingFriendRequests(accountId));
        req.getRequestDispatcher(getJspPagePath("outgoing-friend-requests")).forward(req, resp);
    }

}
