package com.getjavajob.training.timashovy.socialnetwork.web.servlets.account;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.AccountServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.ServiceSingletonRegistry.getServiceSingletonRegistry;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.ServiceSingletonsNames.ACCOUNT_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.ServiceSingletonsNames.MESSAGE_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspPagePaths.ACCOUNT;
import static java.lang.Long.valueOf;

public class AccountInfoServlet extends HttpServlet {

    private final AccountService accountService = getServiceSingletonRegistry().getSingleton(ACCOUNT_SERVICE_SINGLETON);
    private final MessageService messageService = getServiceSingletonRegistry().getSingleton(MESSAGE_SERVICE_SINGLETON);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long accountId = valueOf(req.getParameter("id"));
        if (accountService.getById(accountId).isPresent()) {
            req.setAttribute("account", accountService.getById(accountId).get());
            req.setAttribute("wallPosts", messageService.getAllAccountWallMessages(accountId));
            req.setAttribute("accountService", accountService);
        }
        req.getRequestDispatcher(getJspPagePath(ACCOUNT)).forward(req, resp);
    }

}
