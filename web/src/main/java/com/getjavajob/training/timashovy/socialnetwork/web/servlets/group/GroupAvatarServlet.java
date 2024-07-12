package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServiceSingletonsNames.GROUP_SERVICE_BEAN;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.WebContextUtils.getApplicationContext;
import static java.lang.Long.valueOf;

public class GroupAvatarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GroupService groupService = getApplicationContext(req.getServletContext()).getBean(GROUP_SERVICE_BEAN,
                GroupService.class);
        if (groupService.getById(valueOf(req.getParameter("id"))).isPresent()) {
            req.setAttribute("inputStreamImage", groupService.getById(valueOf(req.getParameter("id"))).get().getAvatar());
        }
        req.getRequestDispatcher("/image/show").forward(req, resp);
    }

}
