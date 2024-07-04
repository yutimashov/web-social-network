package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message.personal;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonRegistry;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.*;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.APPLICATION_CONTEXT;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.SERVICE_SINGLETON_REGISTRY_ATTR;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static java.lang.Long.valueOf;

public class ShowDialogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext ctx = (ApplicationContext) req.getServletContext().getAttribute(APPLICATION_CONTEXT);
        AccountService accountService = (AccountService) ctx.getBean(ACCOUNT_SERVICE_BEAN);
        if (accountService.getById(valueOf(req.getParameter("id"))).isPresent()) {
            req.setAttribute("account", accountService.getById(valueOf(req.getParameter("id"))).get());
        }
        req.setAttribute("accountService", accountService);
        MessageServiceImpl messageService = (MessageService) ctx.getBean(MESSAGE_SERVICE_BEAN);
        req.setAttribute("messages", messageService
                .getAllPersonalMessagesWithAccount(((Account) req.getSession(false).getAttribute("account")).getId(),
                        valueOf(req.getParameter("id"))));
        req.getRequestDispatcher(getJspPagePath("/account/dialog")).forward(req, resp);
    }

}
