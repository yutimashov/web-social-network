package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.ImageService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.GroupAvatarServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.GroupServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;

public class CreateGroupServlet extends HttpServlet {

    private final GroupService groupService = GroupServiceImpl.getInstance();
    private final ImageService avatarService = GroupAvatarServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(getJspPagePath("group/create")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        createGroup(req);
        resp.sendRedirect("/group/all");
    }

    private void createGroup(HttpServletRequest req) throws ServletException, IOException {
        Long accountId = ((Account) req.getSession(false).getAttribute("account")).getId();
        Long groupId = groupService.createGroup(new Group(req.getParameter("name"), req.getParameter("description"),
                accountId));
        avatarService.create(groupId, req.getPart("avatar").getInputStream());
        groupService.sendGroupMemberRequest(groupId, accountId);
        groupService.makeAccountGroupMember(groupId, accountId);
        groupService.makeUserGroupAdmin(groupId, accountId);
    }

}
