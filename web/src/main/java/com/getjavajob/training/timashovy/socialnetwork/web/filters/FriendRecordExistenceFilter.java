package com.getjavajob.training.timashovy.socialnetwork.web.filters;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonRegistry;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.ACCOUNT_SERVICE_SINGLETON;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.SERVICE_SINGLETON_REGISTRY_ATTR;
import static java.lang.Long.valueOf;

public class FriendRecordExistenceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        AccountService accountService = ((ServiceSingletonRegistry) req.getServletContext()
                .getAttribute(SERVICE_SINGLETON_REGISTRY_ATTR)).getSingleton(ACCOUNT_SERVICE_SINGLETON);
        if (accountService.checkFriendshipRecordExistence(((Account) req.getSession(false)
                .getAttribute("account")).getId(), valueOf(req.getParameter("id")))) {
            req.setAttribute("alreadySentFriendRequest", true);
        }
        filterChain.doFilter(req, servletResponse);
    }

}
