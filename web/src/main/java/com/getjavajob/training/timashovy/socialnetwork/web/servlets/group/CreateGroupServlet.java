package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.GROUP_MEMBERSHIP_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.GROUP_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.serviceSingletonRegistry;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;

public class CreateGroupServlet extends HttpServlet {

    private final GroupService groupService = serviceSingletonRegistry.getSingleton(GROUP_SERVICE_SINGLETON);
    private final GroupMembershipService groupMembershipService = serviceSingletonRegistry
            .getSingleton(GROUP_MEMBERSHIP_SERVICE_SINGLETON);

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
        Long groupId = groupService.create(new Group.Builder()
                .groupName(req.getParameter("name"))
                .description(req.getParameter("description"))
                .accountOwnerId(accountId)
                .avatar(req.getPart("avatar").getInputStream())
                .build());
        groupMembershipService.sendRequest(groupId, accountId);
        groupMembershipService.makeMember(groupId, accountId);
        groupMembershipService.makeAdmin(groupId, accountId);
    }

}
