package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonRegistry;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.GROUP_MEMBERSHIP_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.GROUP_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.SERVICE_SINGLETON_REGISTRY_ATTR;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static java.lang.Long.valueOf;

public class GroupMembersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long groupId = valueOf(req.getParameter("id"));
        ServiceSingletonRegistry serviceSingletonRegistry = ((ServiceSingletonRegistry) getServletContext()
                .getAttribute(SERVICE_SINGLETON_REGISTRY_ATTR));
        GroupService groupService = serviceSingletonRegistry.getSingleton(GROUP_SERVICE_SINGLETON);
        GroupMembershipService groupMembershipService = serviceSingletonRegistry
                .getSingleton(GROUP_MEMBERSHIP_SERVICE_SINGLETON);
        if (groupService.getById(groupId).isPresent()) {
            req.setAttribute("group", groupService.getById(groupId).get());
            req.setAttribute("groupMembers", groupMembershipService.getRegularMembers(groupId));
            req.setAttribute("groupAdmins", groupMembershipService.getAdmins(groupId));
        }
        req.getRequestDispatcher(getJspPagePath("group/members")).forward(req, resp);
    }

}
