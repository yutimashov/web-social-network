package com.getjavajob.training.timashovy.socialnetwork.web.servlets.admin;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServiceSingletonsNames.ACCOUNT_SERVICE_BEAN;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspPagePaths.ACCOUNTS;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServletPaths.LOGOUT_SERVLET_PATH;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.WebContextUtils.getApplicationContext;
import static java.lang.Long.valueOf;

public class DeleteAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long accountIdToDelete = valueOf(req.getParameter("id"));
        getApplicationContext(req.getServletContext()).getBean(ACCOUNT_SERVICE_BEAN, AccountService.class)
                .delete(accountIdToDelete);
        if (!Objects.equals(((Account) req.getSession(false).getAttribute("account")).getId(), accountIdToDelete)) {
            req.getRequestDispatcher(getJspPagePath(ACCOUNTS)).forward(req, resp);
        } else {
            req.getRequestDispatcher(LOGOUT_SERVLET_PATH).forward(req, resp);
        }
    }

}
