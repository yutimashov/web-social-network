package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message.group;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import org.springframework.context.ApplicationContext;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.MESSAGE_SERVICE_BEAN;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.APPLICATION_CONTEXT;
import static java.lang.Long.valueOf;

public class ShowImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MessageService messageService = (MessageService) ((ApplicationContext) req.getServletContext()
                .getAttribute(APPLICATION_CONTEXT)).getBean(MESSAGE_SERVICE_BEAN);
        req.setAttribute("inputStreamImage", messageService.getGroupMessageById(valueOf(req.getParameter("id")))
                .getPhoto());
        req.getRequestDispatcher("/image/show").include(req, resp);
    }

}
