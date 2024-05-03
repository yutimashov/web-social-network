package com.getjavajob.training.timashovy.socialnetwork.web.servlets.search;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.SearchService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.search.SearchServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspDestinationPath.getJspPagePath;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.JspPagePaths.SEARCH_RESULT;

public class SearchServlet extends HttpServlet {

    private final SearchService searchService = SearchServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchQuery = req.getParameter("searchQuery");
        req.setAttribute("searchQuery", searchQuery);
        req.setAttribute("accounts", searchService.findAccounts(searchQuery));
        req.setAttribute("groups", searchService.findGroups(searchQuery));
        req.getRequestDispatcher(getJspPagePath(SEARCH_RESULT)).forward(req, resp);
    }

}
