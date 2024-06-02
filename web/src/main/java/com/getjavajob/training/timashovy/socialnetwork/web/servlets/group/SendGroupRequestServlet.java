package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.GROUP_MEMBERSHIP_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.serviceSingletonRegistry;
import static java.lang.Long.valueOf;

public class SendGroupRequestServlet extends HttpServlet {

    private final GroupMembershipService groupMembershipService = serviceSingletonRegistry
            .getSingleton(GROUP_MEMBERSHIP_SERVICE_SINGLETON);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long accountId = ((Account) req.getSession(false).getAttribute("account")).getId();
        groupMembershipService.sendRequest(valueOf(req.getParameter("id")), accountId);
        resp.sendRedirect("/account?id=" + accountId);
    }

}
