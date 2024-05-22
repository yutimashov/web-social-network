package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonRegistry.getServiceSingletonRegistry;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.GROUP_SERVICE_SINGLETON;
import static java.lang.Long.valueOf;

public class GroupAvatarServlet extends HttpServlet {

    private final GroupService groupService = getServiceSingletonRegistry().getSingleton(GROUP_SERVICE_SINGLETON);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (groupService.getById(valueOf(req.getParameter("id"))).isPresent()) {
            req.setAttribute("inputStreamImage", groupService.getById(valueOf(req.getParameter("id"))).get().getAvatar());
        }
        req.getRequestDispatcher("/image/show").forward(req, resp);
    }

}
