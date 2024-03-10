package com.getjavajob.training.timashovy.socialnetwork.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.LoginService.getLoginServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(getJspPagePath("login")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userEnteredEmail = req.getParameter("email");
        String userEnteredPassword = req.getParameter("password");
        if (getLoginServiceInstance().login(userEnteredEmail, userEnteredPassword)) {
            req.getRequestDispatcher(getJspPagePath("accounts/page")).forward(req, resp);
        } else {
            resp.sendRedirect("/login?error");
        }
    }

}
