package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message.group;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonRegistry;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.MESSAGE_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.SERVICE_SINGLETON_REGISTRY_ATTR;
import static java.lang.Long.valueOf;

public class ShowImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceSingletonRegistry serviceSingletonRegistry = ((ServiceSingletonRegistry) getServletContext()
                .getAttribute(SERVICE_SINGLETON_REGISTRY_ATTR));
        MessageService messageService = serviceSingletonRegistry.getSingleton(MESSAGE_SERVICE_SINGLETON);
        req.setAttribute("inputStreamImage", messageService.getGroupMessageById(valueOf(req.getParameter("id")))
                .getPhoto());
        req.getRequestDispatcher("/image/show").include(req, resp);
    }

}
