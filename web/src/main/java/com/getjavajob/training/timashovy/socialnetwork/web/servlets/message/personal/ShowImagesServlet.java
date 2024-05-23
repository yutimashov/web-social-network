package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message.personal;

import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonRegistry.getServiceSingletonRegistry;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.MESSAGE_SERVICE_SINGLETON;
import static java.lang.Long.valueOf;

public class ShowImagesServlet extends HttpServlet {

    private final MessageServiceImpl messageService = getServiceSingletonRegistry()
            .getSingleton(MESSAGE_SERVICE_SINGLETON);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("inputStreamImage", messageService.getPersonalMessageById(valueOf(req.getParameter("id")))
                .getPhoto());
        req.getRequestDispatcher("/image/show").include(req, resp);
    }

}
