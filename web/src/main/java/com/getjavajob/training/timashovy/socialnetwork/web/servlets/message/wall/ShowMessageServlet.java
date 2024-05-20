package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message.wall;

import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.ServiceSingletonRegistry.getServiceSingletonRegistry;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.ServiceSingletonsNames.MESSAGE_SERVICE_SINGLETON;
import static java.lang.Long.valueOf;

public class ShowMessageServlet extends HttpServlet {

    private final MessageServiceImpl messageService = getServiceSingletonRegistry()
            .getSingleton(MESSAGE_SERVICE_SINGLETON);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("inputStreamImage", messageService.getAccountWallMessageById(valueOf(req.getParameter("id")))
                .getPhoto());
        req.getRequestDispatcher("/image/show").include(req, resp);
    }

}
