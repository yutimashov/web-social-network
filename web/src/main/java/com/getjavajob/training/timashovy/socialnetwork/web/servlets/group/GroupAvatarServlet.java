package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group.GroupAvatarServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group.GroupAvatarServiceImpl.getInstance;
import static java.lang.Long.valueOf;

public class GroupAvatarServlet extends HttpServlet {

    private final GroupAvatarServiceImpl avatarService = getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("inputStreamImage", avatarService.get(valueOf(req.getParameter("id"))));
        req.getRequestDispatcher("/image/show").forward(req, resp);
    }

}
