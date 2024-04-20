package com.getjavajob.training.timashovy.socialnetwork.web.servlets.admin;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl.getInstance;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static java.lang.Long.valueOf;

public class DeleteAccountServlet extends HttpServlet {

    private final AccountService accountService = getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        accountService.deleteAccount(valueOf(req.getParameter("id")));
        Long sessionAccountId = ((Account) req.getSession(false).getAttribute("account")).getId();
        Long accountId = Long.valueOf(req.getParameter("id"));
        if (!Objects.equals(sessionAccountId, accountId)) {
            req.getRequestDispatcher(getJspPagePath("/admin/delete")).forward(req, resp);
        } else {
            req.getSession().invalidate();
            resp.sendRedirect("/login");
        }
    }

}
