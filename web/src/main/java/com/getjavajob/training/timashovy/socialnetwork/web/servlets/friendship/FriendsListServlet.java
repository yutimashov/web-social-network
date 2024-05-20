package com.getjavajob.training.timashovy.socialnetwork.web.servlets.friendship;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.ServiceSingletonRegistry.getServiceSingletonRegistry;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.ServiceSingletonsNames.ACCOUNT_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspPagePaths.FRIENDS;
import static java.lang.Long.valueOf;

public class FriendsListServlet extends HttpServlet {

    private final AccountService accountService = getServiceSingletonRegistry().getSingleton(ACCOUNT_SERVICE_SINGLETON);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("friends", accountService.getFriends(valueOf(req.getParameter("id"))));
        req.getRequestDispatcher(getJspPagePath(FRIENDS)).forward(req, resp);
    }

}
