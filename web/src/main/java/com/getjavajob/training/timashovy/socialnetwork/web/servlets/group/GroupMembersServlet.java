package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.GroupServiceImpl.getInstance;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static java.lang.Long.valueOf;

public class GroupMembersServlet extends HttpServlet {

    private final GroupService groupService = getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long groupId = valueOf(req.getParameter("id"));
        if (groupService.getById(groupId).isPresent()) {
            req.setAttribute("group", groupService.getById(groupId).get());
            Long accountId = ((Account) req.getSession(false).getAttribute("account")).getId();
            req.setAttribute("groupMembers", groupService.getGroupMembers(groupId));
        }
        req.getRequestDispatcher(getJspPagePath("group-members")).forward(req, resp);
    }

}
