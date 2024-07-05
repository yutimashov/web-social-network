package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message.wall;

import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.MESSAGE_SERVICE_BEAN;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.APPLICATION_CONTEXT;
import static java.lang.Long.valueOf;

public class ShowMessageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MessageServiceImpl messageService = (MessageServiceImpl) ((ApplicationContext) req.getServletContext()
                .getAttribute(APPLICATION_CONTEXT)).getBean(MESSAGE_SERVICE_BEAN);
        req.setAttribute("inputStreamImage", messageService.getAccountWallMessageById(valueOf(req.getParameter("id")))
                .getPhoto());
        req.getRequestDispatcher("/image/show").include(req, resp);
    }

}
