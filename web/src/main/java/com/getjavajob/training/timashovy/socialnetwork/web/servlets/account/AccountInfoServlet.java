package com.getjavajob.training.timashovy.socialnetwork.web.servlets.account;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.*;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.APPLICATION_CONTEXT;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspPagePaths.ACCOUNT;
import static java.lang.Long.valueOf;

public class AccountInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long accountId = valueOf(req.getParameter("id"));
        ApplicationContext ctx = (ApplicationContext) req.getServletContext()
                .getAttribute(APPLICATION_CONTEXT);
        AccountService accountService = (AccountService) ctx.getBean(ACCOUNT_SERVICE_BEAN);
        MessageService messageService = (MessageService) ctx.getBean(MESSAGE_SERVICE_BEAN);
        if (accountService.getById(accountId).isPresent()) {
            req.setAttribute("account", accountService.getById(accountId).get());
            req.setAttribute("wallPosts", messageService.getAllAccountWallMessages(accountId));
            req.setAttribute("accountService", accountService);
        }
        req.getRequestDispatcher(getJspPagePath(ACCOUNT)).forward(req, resp);
    }

}
