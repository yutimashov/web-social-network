package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import static java.lang.Long.valueOf;

public class ShowMessageImageServlet extends HttpServlet {

    private final MessageService messageService = MessageServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("inputStreamImage", messageService.getById(valueOf(req.getParameter("id"))).getPhoto());
        req.getRequestDispatcher("/image/show").include(req, resp);
    }

}
