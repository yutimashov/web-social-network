package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServiceSingletonsNames.GROUP_MEMBERSHIP_SERVICE_BEAN;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.WebContextUtils.getApplicationContext;
import static java.lang.Long.valueOf;

public class MakeGroupAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getApplicationContext(req.getServletContext()).getBean(GROUP_MEMBERSHIP_SERVICE_BEAN,
                GroupMembershipService.class).makeAdmin(valueOf(req.getParameter("groupId")),
                valueOf(req.getParameter("accountId")));
        resp.sendRedirect("/group?id=" + valueOf(req.getParameter("groupId")));
    }

}
