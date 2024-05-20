package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message.personal;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
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
import static java.lang.Long.valueOf;

public class ShowDialogServlet extends HttpServlet {

    private final AccountService accountService = getServiceSingletonRegistry()
            .getSingleton(ACCOUNT_SERVICE_SINGLETON);
    private final MessageServiceImpl messageService = getServiceSingletonRegistry()
            .getSingleton(MESSAGE_SERVICE_SINGLETON);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (accountService.getById(valueOf(req.getParameter("id"))).isPresent()) {
            req.setAttribute("account", accountService.getById(valueOf(req.getParameter("id"))).get());
        }
        req.setAttribute("accountService", accountService);
        req.setAttribute("messages", messageService
                .getAllPersonalMessagesWithAccount(((Account) req.getSession(false).getAttribute("account")).getId(),
                        valueOf(req.getParameter("id"))));
        req.getRequestDispatcher(getJspPagePath("/account/dialog")).forward(req, resp);
    }

}
