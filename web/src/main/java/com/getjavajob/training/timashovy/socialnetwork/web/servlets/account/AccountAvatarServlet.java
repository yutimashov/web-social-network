package com.getjavajob.training.timashovy.socialnetwork.web.servlets.account;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServiceSingletonsNames.ACCOUNT_SERVICE_BEAN;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServletPaths.SHOW_IMAGE_SERVLET_PATH;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.WebContextUtils.getApplicationContext;
import static java.lang.Long.valueOf;

public class AccountAvatarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountService accountService = getApplicationContext(req.getServletContext()).getBean(ACCOUNT_SERVICE_BEAN,
                AccountService.class);
        if (accountService.getById(valueOf(req.getParameter("id"))).isPresent()) {
            req.setAttribute("inputStreamImage", accountService.getById(valueOf(req.getParameter("id")))
                    .get().getAvatar());
            req.getRequestDispatcher(SHOW_IMAGE_SERVLET_PATH).include(req, resp);
        }
    }

}
