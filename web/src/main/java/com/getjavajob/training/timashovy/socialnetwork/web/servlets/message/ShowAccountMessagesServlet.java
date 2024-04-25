package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message;

import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static java.lang.Long.valueOf;

public class ShowAccountMessagesServlet extends HttpServlet {

    private final MessageServiceImpl messageService = MessageServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("accounts", messageService.getAllAccountsWithPersonalMessages(valueOf(req.getParameter("id"))));
        req.getRequestDispatcher(getJspPagePath("account/messages")).forward(req, resp);
    }

}
