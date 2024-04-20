package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.GroupServiceImpl.getInstance;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static java.lang.Long.valueOf;

public class IncomingGroupRequestsServlet extends HttpServlet {

    private final GroupService groupService = getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long groupId = valueOf(req.getParameter("id"));
        req.setAttribute("groupRequests", groupService.getIncomingGroupRequests(groupId));
        req.setAttribute("groupId", groupId);
        req.getRequestDispatcher(getJspPagePath("incoming-group-requests")).forward(req, resp);
    }

}
