package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message.personal;

import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.MESSAGE_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.serviceSingletonRegistry;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static java.lang.Long.valueOf;

public class ShowMessagesServlet extends HttpServlet {

    private final MessageServiceImpl messageService = serviceSingletonRegistry.getSingleton(MESSAGE_SERVICE_SINGLETON);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("accounts", messageService
                .getAllAccountsWithPersonalMessages(valueOf(req.getParameter("id"))));
        req.getRequestDispatcher(getJspPagePath("account/messages")).forward(req, resp);
    }

}
