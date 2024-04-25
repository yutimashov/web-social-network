package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.AccountServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;

public class ShowPrivateMessageDialogServlet extends HttpServlet {

    private final AccountService accountService = AccountServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (accountService.getAccountById(Long.valueOf(req.getParameter("id"))).isPresent()) {
            req.setAttribute("account", accountService.getAccountById(Long.valueOf(req.getParameter("id"))).get());
        }

        req.getRequestDispatcher(getJspPagePath("/account/dialog")).forward(req, resp);
    }

}
