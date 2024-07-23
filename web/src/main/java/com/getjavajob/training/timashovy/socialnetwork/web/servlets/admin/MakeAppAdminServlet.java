package com.getjavajob.training.timashovy.socialnetwork.web.servlets.admin;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AdminService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServiceSingletonsNames.ADMIN_SERVICE_BEAN;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServletPaths.ACCOUNT_SERVLET_PATH;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.WebContextUtils.getApplicationContext;
import static java.lang.Long.valueOf;

public class MakeAppAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getApplicationContext(req.getServletContext()).getBean(ADMIN_SERVICE_BEAN, AdminService.class)
                .makeAdmin(valueOf(req.getParameter("id")));
        resp.sendRedirect(ACCOUNT_SERVLET_PATH + "?id=" + req.getParameter("id"));
    }

}
