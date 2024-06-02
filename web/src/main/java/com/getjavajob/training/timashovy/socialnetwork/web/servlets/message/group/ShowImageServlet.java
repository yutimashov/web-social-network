package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message.group;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.MESSAGE_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.serviceSingletonRegistry;
import static java.lang.Long.valueOf;

public class ShowImageServlet extends HttpServlet {

    private final MessageService messageService = serviceSingletonRegistry.getSingleton(MESSAGE_SERVICE_SINGLETON);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("inputStreamImage", messageService.getGroupMessageById(valueOf(req.getParameter("id")))
                .getPhoto());
        req.getRequestDispatcher("/image/show").include(req, resp);
    }

}
