package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message.personal;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServiceSingletonsNames.ACCOUNT_SERVICE_BEAN;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServiceSingletonsNames.MESSAGE_SERVICE_BEAN;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.WebContextUtils.getApplicationContext;
import static java.lang.Long.valueOf;

public class ShowDialogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext ctx = getApplicationContext(req.getServletContext());
        AccountService accountService = ctx.getBean(ACCOUNT_SERVICE_BEAN, AccountService.class);
        if (accountService.getById(valueOf(req.getParameter("id"))).isPresent()) {
            req.setAttribute("account", accountService.getById(valueOf(req.getParameter("id"))).get());
        }
        req.setAttribute("accountService", accountService);
        MessageServiceImpl messageService = ctx.getBean(MESSAGE_SERVICE_BEAN, MessageServiceImpl.class);
        req.setAttribute("messages", messageService
                .getAllPersonalMessagesWithAccount(((Account) req.getSession(false).getAttribute("account")).getId(),
                        valueOf(req.getParameter("id"))));
        req.getRequestDispatcher(getJspPagePath("/account/dialog")).forward(req, resp);
    }

}
