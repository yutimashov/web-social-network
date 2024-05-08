package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group.GroupServiceImpl.getInstance;
import static java.lang.Long.valueOf;

public class DeleteGroupMemberServlet extends HttpServlet {

    private final GroupService groupService = getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long groupId = valueOf(req.getParameter("groupId"));
        groupService.deleteMember(groupId, valueOf(req.getParameter("accountId")));
        resp.sendRedirect("/group?id=" + groupId);
    }

}
