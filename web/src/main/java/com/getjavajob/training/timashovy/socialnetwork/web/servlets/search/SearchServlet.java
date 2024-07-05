package com.getjavajob.training.timashovy.socialnetwork.web.servlets.search;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.SearchService;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.*;
import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsHolderListener.APPLICATION_CONTEXT;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspPagePaths.SEARCH_RESULT;
import static java.lang.Integer.parseInt;

public class SearchServlet extends HttpServlet {

    private static final int RESULTS_PER_PAGE = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchQuery = req.getParameter("searchQuery");
        req.setAttribute("searchQuery", searchQuery);
        int currentPage = parseInt(req.getParameter("currentPage"));
        req.setAttribute("currentPage", currentPage);
        String searchType = req.getParameter("searchType");
        req.setAttribute("searchType", searchType);
        int numberOfPages = 0;
        SearchService searchService = (SearchService) ((ApplicationContext) req.getServletContext()
                .getAttribute(APPLICATION_CONTEXT)).getBean(SEARCH_SERVICE_BEAN);
        if ("account".equals(searchType)) {
            List<Account> accounts = searchService.findAccounts(searchQuery, currentPage, RESULTS_PER_PAGE);
            req.setAttribute("accounts", accounts);
            numberOfPages = searchService.findAccountResultsAmount(searchQuery) / RESULTS_PER_PAGE;
        } else if ("group".equals(searchType)) {
            List<Group> groups = searchService.findGroups(searchQuery, currentPage, RESULTS_PER_PAGE);
            req.setAttribute("groups", groups);
            numberOfPages = searchService.findGroupResultsAmount(searchQuery) / RESULTS_PER_PAGE;
        }
        if (numberOfPages % RESULTS_PER_PAGE > 0) {
            numberOfPages++;
        }
        req.setAttribute("numberOfPages", numberOfPages);
        req.setAttribute("recordsPerPage", RESULTS_PER_PAGE);
        req.getRequestDispatcher(getJspPagePath(SEARCH_RESULT)).forward(req, resp);
    }

}
