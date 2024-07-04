package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import org.springframework.context.ApplicationContext;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.*;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.APPLICATION_CONTEXT;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static java.lang.Long.valueOf;

public class GroupMembersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long groupId = valueOf(req.getParameter("id"));
        ApplicationContext ctx = (ApplicationContext) req.getServletContext().getAttribute(APPLICATION_CONTEXT);
        GroupService groupService = (GroupService) ctx.getBean(GROUP_SERVICE_BEAN);
        GroupMembershipService groupMembershipService = (GroupMembershipService) ctx
                .getBean(GROUP_MEMBERSHIP_SERVICE_BEAN);
        if (groupService.getById(groupId).isPresent()) {
            req.setAttribute("group", groupService.getById(groupId).get());
            req.setAttribute("groupMembers", groupMembershipService.getRegularMembers(groupId));
            req.setAttribute("groupAdmins", groupMembershipService.getAdmins(groupId));
        }
        req.getRequestDispatcher(getJspPagePath("group/members")).forward(req, resp);
    }

}
