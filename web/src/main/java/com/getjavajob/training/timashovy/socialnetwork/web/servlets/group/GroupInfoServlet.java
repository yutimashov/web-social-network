package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.GroupAvatarServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.GroupServiceImpl;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long groupId = valueOf(req.getParameter("id"));
        if (groupService.getById(groupId).isPresent()) {
            req.setAttribute("group", groupService.getById(groupId).get());
            req.setAttribute("avatarInputStream", avatarService.get(groupId));
            Long accountId = ((Account) req.getSession(false).getAttribute("account")).getId();
            if (groupService.isAccountAdmin(groupId, accountId)) {
                req.setAttribute("isAccountAdmin", true);
            }
            if (groupService.isAccountGroupSubscriber(groupId, accountId)) {
                req.setAttribute("isSubscriber", true);
            }
            if (groupService.isAccountGroupMember(groupId, accountId)) {
                req.setAttribute("isMember", true);
            }
        }
        req.getRequestDispatcher(getJspPagePath("group/group")).forward(req, resp);
    }

}
