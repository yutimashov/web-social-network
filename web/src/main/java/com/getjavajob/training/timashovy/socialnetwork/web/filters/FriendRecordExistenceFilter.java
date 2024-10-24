package com.getjavajob.training.timashovy.socialnetwork.web.filters;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.WebContextUtils.getApplicationContext;
import static java.lang.Long.valueOf;

public class FriendRecordExistenceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        AccountService accountService = getApplicationContext(req.getServletContext()).getBean("accountService",
                AccountService.class);
        if (accountService.checkFriendshipRecordExistence(((Account) req.getSession(false)
                .getAttribute("account")).getId(), valueOf(req.getParameter("id")))) {
            req.setAttribute("alreadySentFriendRequest", true);
        }
        filterChain.doFilter(req, servletResponse);
    }

}
