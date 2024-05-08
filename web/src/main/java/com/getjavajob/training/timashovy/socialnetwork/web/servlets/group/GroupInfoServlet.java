package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.AccountServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group.GroupAvatarServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group.GroupServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static java.lang.Long.valueOf;

public class GroupInfoServlet extends HttpServlet {

    private final GroupService groupService = GroupServiceImpl.getInstance();
    private final GroupAvatarServiceImpl avatarService = GroupAvatarServiceImpl.getInstance();
    private final MessageService messageService = MessageServiceImpl.getInstance();
    private final AccountService accountService = AccountServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long groupId = valueOf(req.getParameter("id"));
        if (groupService.getById(groupId).isPresent()) {
            req.setAttribute("group", groupService.getById(groupId).get());
            req.setAttribute("avatarInputStream", avatarService.get(groupId));
            Long accountId = ((Account) req.getSession(false).getAttribute("account")).getId();
            req.setAttribute("isAdmin", groupService.isAdmin(groupId, accountId));
            req.setAttribute("isSubscriber", groupService.isSubscriber(groupId, accountId));
            req.setAttribute("isMember", groupService.isMember(groupId, accountId));
            req.setAttribute("groupPosts", messageService.getAllGroupMessages(groupId));
            req.setAttribute("accountService", accountService);
        }
        req.getRequestDispatcher(getJspPagePath("group/group")).forward(req, resp);
    }

}
