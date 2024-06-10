package com.getjavajob.training.timashovy.socialnetwork.web.servlets.auth;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.util.AccountRegistrationData;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonRegistry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.ACCOUNT_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.SERVICE_SINGLETON_REGISTRY_ATTR;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspPagePaths.REGISTER;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.ServletPaths.LOGIN_SERVLET_PATH;

public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(getJspPagePath(REGISTER)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        AccountService accountService = ((ServiceSingletonRegistry) getServletContext()
                .getAttribute(SERVICE_SINGLETON_REGISTRY_ATTR)).getSingleton(ACCOUNT_SERVICE_SINGLETON);
        accountService.create(new AccountRegistrationData.Builder()
                .account(
                        new Account.Builder()
                                .avatar(req.getPart("avatar") != null && req.getPart("avatar").getSize() > 0
                                        ? req.getPart("avatar").getInputStream() : null)
                                .firstName(req.getParameter("name"))
                                .lastName(req.getParameter("lastName"))
                                .middleName(req.getParameter("middleName"))
                                .email(req.getParameter("email"))
                                .skype(req.getParameter("skype"))
                                .icq(req.getParameter("icq"))
                                .build()
                )
                .password(req.getParameter("password"))
                .personalPhoneNumber(req.getParameter("personalPhoneNumber"))
                .workPhoneNumber(req.getParameter("workPhoneNumber"))
                .build()
        );
        resp.sendRedirect(LOGIN_SERVLET_PATH);
    }

}
