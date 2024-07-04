package com.getjavajob.training.timashovy.socialnetwork.web.filters;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import org.springframework.context.ApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.ACCOUNT_SERVICE_BEAN;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.APPLICATION_CONTEXT;
import static java.lang.Long.valueOf;

public class FriendRecordExistenceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        AccountService accountService = (AccountService) ((ApplicationContext) req.getServletContext()
                .getAttribute(APPLICATION_CONTEXT)).getBean(ACCOUNT_SERVICE_BEAN);
        if (accountService.checkFriendshipRecordExistence(((Account) req.getSession(false)
                .getAttribute("account")).getId(), valueOf(req.getParameter("id")))) {
            req.setAttribute("alreadySentFriendRequest", true);
        }
        filterChain.doFilter(req, servletResponse);
    }

}
