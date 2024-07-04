package com.getjavajob.training.timashovy.socialnetwork.web.servlets.admin;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AdminService;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.ADMIN_SERVICE_BEAN;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.APPLICATION_CONTEXT;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServletPaths.ACCOUNT_SERVLET_PATH;
import static java.lang.Long.valueOf;

public class MakeAppAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdminService adminService = (AdminService) ((ApplicationContext) req.getServletContext()
                .getAttribute(APPLICATION_CONTEXT)).getBean(ADMIN_SERVICE_BEAN);
        adminService.makeAdmin(valueOf(req.getParameter("id")));
        resp.sendRedirect(ACCOUNT_SERVLET_PATH + "?id=" + req.getParameter("id"));
    }

}
