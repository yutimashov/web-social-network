package com.getjavajob.training.timashovy.socialnetwork.web.servlets.friendship;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.ServiceSingletonRegistry.getServiceSingletonRegistry;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.ServiceSingletonsNames.ACCOUNT_SERVICE_SINGLETON;
import static java.lang.Long.valueOf;

public class AcceptFriendshipRequestServlet extends HttpServlet {

    AccountService accountService = getServiceSingletonRegistry().getSingleton(ACCOUNT_SERVICE_SINGLETON);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long accepterAccountId = ((Account) req.getSession(false).getAttribute("account")).getId();
        Long requesterAccountId = valueOf(req.getParameter("id"));
        accountService.addFriend(requesterAccountId, accepterAccountId);
        resp.sendRedirect("/friends?id=" + accepterAccountId);
    }

}
