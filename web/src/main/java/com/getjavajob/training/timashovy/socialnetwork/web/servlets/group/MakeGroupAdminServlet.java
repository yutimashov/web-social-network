package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.ServiceSingletonRegistry.getServiceSingletonRegistry;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.ServiceSingletonsNames.GROUP_SERVICE_SINGLETON;
import static java.lang.Long.valueOf;

public class MakeGroupAdminServlet extends HttpServlet {

    private final GroupService groupService = getServiceSingletonRegistry().getSingleton(GROUP_SERVICE_SINGLETON);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        groupService.makeAdmin(valueOf(req.getParameter("groupId")),
                valueOf(req.getParameter("accountId")));
        resp.sendRedirect("/group?id=" + valueOf(req.getParameter("groupId")));
    }

}
