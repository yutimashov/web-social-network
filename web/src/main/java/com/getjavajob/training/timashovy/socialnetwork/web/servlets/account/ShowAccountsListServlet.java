package com.getjavajob.training.timashovy.socialnetwork.web.servlets.account;

import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.ACCOUNT_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.serviceSingletonRegistry;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspPagePaths.ALL_ACCOUNTS;

public class ShowAccountsListServlet extends HttpServlet {

    private final AccountServiceImpl accountService = serviceSingletonRegistry.getSingleton(ACCOUNT_SERVICE_SINGLETON);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("accounts", accountService.getAll());
        req.getRequestDispatcher(getJspPagePath(ALL_ACCOUNTS)).forward(req, resp);
    }

}
