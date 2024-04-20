package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.ImageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.GroupAvatarServiceImpl.getGroupAvatarServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.GroupServiceImpl.getGroupServiceImplInstance;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;

public class CreateGroupServlet extends HttpServlet {

    private final GroupService groupService = getGroupServiceImplInstance();
    private final ImageService avatarService = getGroupAvatarServiceInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(getJspPagePath("group-create")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long accountId = ((Account) req.getSession(false).getAttribute("account")).getId();
        Group group = new Group(
                req.getParameter("name"),
                req.getParameter("description"),
                accountId
        );
        Long groupId = groupService.createGroup(group);
        avatarService.create(groupId, req.getPart("avatar").getInputStream());
        groupService.sendGroupMemberRequest(groupId, accountId);
        groupService.makeAccountGroupMember(groupId, accountId);
        groupService.makeUserGroupAdmin(groupId, accountId);
        resp.sendRedirect("/account?id=" + ((Account) req.getSession(false).getAttribute("account")).getId());
    }

}
