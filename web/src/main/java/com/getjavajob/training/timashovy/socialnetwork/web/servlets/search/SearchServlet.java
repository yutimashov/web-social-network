package com.getjavajob.training.timashovy.socialnetwork.web.servlets.search;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.SearchService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.search.SearchServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspPagePaths.SEARCH_RESULT;
import static java.lang.Integer.parseInt;

public class SearchServlet extends HttpServlet {

    private final SearchService searchService = SearchServiceImpl.getInstance();
    private static final int RESULTS_PER_PAGE = 5;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchQuery = req.getParameter("searchQuery");
        req.setAttribute("searchQuery", searchQuery);

        int currentPage = parseInt(req.getParameter("currentPage"));
        int recordsPerPage = parseInt(req.getParameter("recordsPerPage"));

        List<Account> accounts = searchService.findAccounts(searchQuery, currentPage, RESULTS_PER_PAGE);
        req.setAttribute("accounts", accounts);
        List<Group> groups = searchService.findGroups(searchQuery, currentPage, RESULTS_PER_PAGE);
        req.setAttribute("groups", groups);

        // общее количество записей для вывода всех значений
        int accountRows = accounts.size();
        int groupRows = groups.size();
        int totalNumberOfPages = (accountRows + groupRows) / RESULTS_PER_PAGE;
        if (totalNumberOfPages % RESULTS_PER_PAGE > 0) {
            totalNumberOfPages++;
        }

        req.setAttribute("numberOfPages", totalNumberOfPages);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("recordsPerPage", RESULTS_PER_PAGE);

        req.getRequestDispatcher(getJspPagePath(SEARCH_RESULT)).forward(req, resp);
    }

}
