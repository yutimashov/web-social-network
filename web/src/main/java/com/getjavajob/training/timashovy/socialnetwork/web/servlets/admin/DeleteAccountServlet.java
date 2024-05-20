package com.getjavajob.training.timashovy.socialnetwork.web.servlets.admin;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.ServiceSingletonRegistry.getServiceSingletonRegistry;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.ServiceSingletonsNames.ACCOUNT_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspPagePaths.ACCOUNTS;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServletPaths.LOGOUT_SERVLET_PATH;
import static java.lang.Long.valueOf;

public class DeleteAccountServlet extends HttpServlet {

    private final AccountService accountService = getServiceSingletonRegistry().getSingleton(ACCOUNT_SERVICE_SINGLETON);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long accountIdToDelete = valueOf(req.getParameter("id"));
        accountService.delete(accountIdToDelete);
        if (!Objects.equals(((Account) req.getSession(false).getAttribute("account")).getId(), accountIdToDelete)) {
            req.getRequestDispatcher(getJspPagePath(ACCOUNTS)).forward(req, resp);
        } else {
            req.getRequestDispatcher(LOGOUT_SERVLET_PATH).forward(req, resp);
        }
    }

}
