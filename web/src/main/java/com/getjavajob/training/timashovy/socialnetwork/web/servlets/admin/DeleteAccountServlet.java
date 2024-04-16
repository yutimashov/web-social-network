package com.getjavajob.training.timashovy.socialnetwork.web.servlets.admin;

import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl.getInstance;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static java.lang.Long.valueOf;

public class DeleteAccountServlet extends HttpServlet {

    private final AccountServiceImpl accountService = getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        deleteAccount(req);
        req.getRequestDispatcher(getJspPagePath("delete-account")).forward(req, resp);
    }

    private void deleteAccount(HttpServletRequest req) {
        Long deletingAccountId = valueOf(req.getParameter("id"));
        accountService.deleteAccount(deletingAccountId);
    }

}
