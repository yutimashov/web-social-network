package com.getjavajob.training.timashovy.socialnetwork.web.servlets.friendship;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServiceSingletonsNames.ACCOUNT_SERVICE_BEAN;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspPagePaths.FRIENDS;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.WebContextUtils.getApplicationContext;
import static java.lang.Long.valueOf;

public class FriendsListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("friends", getApplicationContext(req.getServletContext()).getBean(ACCOUNT_SERVICE_BEAN,
                AccountService.class).getFriends(valueOf(req.getParameter("id"))));
        req.getRequestDispatcher(getJspPagePath(FRIENDS)).forward(req, resp);
    }

}
