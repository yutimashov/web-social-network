package com.getjavajob.training.timashovy.socialnetwork.web.servlets.friendship;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.ACCOUNT_SERVICE_BEAN;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.APPLICATION_CONTEXT;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspPagePaths.FRIENDS;
import static java.lang.Long.valueOf;

public class FriendsListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountService accountService = (AccountService) ((ApplicationContext) req.getServletContext()
                .getAttribute(APPLICATION_CONTEXT)).getBean(ACCOUNT_SERVICE_BEAN);
        req.setAttribute("friends", accountService.getFriends(valueOf(req.getParameter("id"))));
        req.getRequestDispatcher(getJspPagePath(FRIENDS)).forward(req, resp);
    }

}
