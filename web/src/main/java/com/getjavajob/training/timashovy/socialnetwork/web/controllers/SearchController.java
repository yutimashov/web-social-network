package com.getjavajob.training.timashovy.socialnetwork.web.controllers;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/search")
@Controller
public class SearchController {

    private static final int RESULTS_PER_PAGE = 5;
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    protected String doGet(@RequestParam("searchQuery") String searchQuery,
                           @RequestParam("currentPage") int currentPage,
                           @RequestParam("searchType") String searchType,
                           Model model) {
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("searchType", searchType);
        int numberOfPages = 0;
        if ("account".equals(searchType)) {
            numberOfPages = handleAccountSearch(searchQuery, currentPage, model);
        } else if ("group".equals(searchType)) {
            numberOfPages = handleGroupSearch(searchQuery, currentPage, model);
        }
        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("recordsPerPage", RESULTS_PER_PAGE);
        return "/search/result";
    }

    private int handleAccountSearch(String searchQuery, int currentPage, Model model) {
        model.addAttribute("accounts", searchService.findAccounts(searchQuery, currentPage, RESULTS_PER_PAGE));
        return calculateNumberOfPages(searchService.findAccountResultsAmount(searchQuery));
    }

    private int handleGroupSearch(String searchQuery, int currentPage, Model model) {
        model.addAttribute("groups", searchService.findGroups(searchQuery, currentPage, RESULTS_PER_PAGE));
        return calculateNumberOfPages(searchService.findGroupResultsAmount(searchQuery));
    }

    private int calculateNumberOfPages(int totalResults) {
        return (int) Math.ceil((double) totalResults / RESULTS_PER_PAGE);
    }

}
