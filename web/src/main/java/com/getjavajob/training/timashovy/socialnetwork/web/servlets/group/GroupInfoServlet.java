package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonRegistry.getServiceSingletonRegistry;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.*;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static java.lang.Long.valueOf;

public class GroupInfoServlet extends HttpServlet {

    private final GroupService groupService = getServiceSingletonRegistry().getSingleton(GROUP_SERVICE_SINGLETON);
    private final MessageService messageService = getServiceSingletonRegistry().getSingleton(MESSAGE_SERVICE_SINGLETON);
    private final AccountService accountService = getServiceSingletonRegistry().getSingleton(ACCOUNT_SERVICE_SINGLETON);
    private final GroupMembershipService groupMembershipService = getServiceSingletonRegistry()
            .getSingleton(GROUP_MEMBERSHIP_SERVICE_SINGLETON);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long groupId = valueOf(req.getParameter("id"));
        if (groupService.getById(groupId).isPresent()) {
            req.setAttribute("group", groupService.getById(groupId).get());
            req.setAttribute("avatarInputStream", groupService.getById(groupId).get().getAvatar());
            Long accountId = ((Account) req.getSession(false).getAttribute("account")).getId();
            req.setAttribute("isAdmin", groupMembershipService.isAdmin(groupId, accountId));
            req.setAttribute("isSubscriber", groupMembershipService.isSubscriber(groupId, accountId));
            req.setAttribute("isMember", groupMembershipService.isMember(groupId, accountId));
            req.setAttribute("groupPosts", messageService.getAllGroupMessages(groupId));
            req.setAttribute("accountService", accountService);
        }
        req.getRequestDispatcher(getJspPagePath("group/group")).forward(req, resp);
    }

}
