package com.getjavajob.training.timashovy.socialnetwork.web.controllers;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {

    private static final int RESULTS_PER_PAGE = 5;
    private static final int INITIAL_PAGINATION_PAGE = 1;
    private static final int TIPS_PER_AJAX_REQUEST = 10;
    private static final String ACCOUNT_SEARCH_TYPE = "account";
    private static final String GROUP_SEARCH_TYPE = "group";
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public String doGet(@RequestParam("searchQuery") String searchQuery,
                        @RequestParam("searchType") String searchType,
                        Model model) {
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("searchType", searchType);
        long numberOfPages = 0;
        if (ACCOUNT_SEARCH_TYPE.equals(searchType)) {
            numberOfPages = handleAccountSearch(searchQuery, INITIAL_PAGINATION_PAGE, model);
        } else if (GROUP_SEARCH_TYPE.equals(searchType)) {
            numberOfPages = handleGroupSearch(searchQuery, INITIAL_PAGINATION_PAGE, model);
        }
        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("recordsPerPage", RESULTS_PER_PAGE);
        return "/search/result";
    }

    private Long handleAccountSearch(String searchQuery, int currentPage, Model model) {
        model.addAttribute("accounts", searchService.findAccounts(searchQuery, currentPage, RESULTS_PER_PAGE));
        return calculateNumberOfPages(searchService.findAccountResultsAmount(searchQuery));
    }

    private Long handleGroupSearch(String searchQuery, int currentPage, Model model) {
        model.addAttribute("groups", searchService.findGroups(searchQuery, currentPage, RESULTS_PER_PAGE));
        return calculateNumberOfPages(searchService.findGroupResultsAmount(searchQuery));
    }

    private Long calculateNumberOfPages(long totalResults) {
        return (long) Math.ceil((double) totalResults / RESULTS_PER_PAGE);
    }

    @GetMapping("/search_ajax")
    public ModelAndView search(@RequestParam("searchQuery") String searchQuery,
                               @RequestParam("searchType") String searchType,
                               @RequestParam("currentPage") int currentPage,
                               ModelAndView modelAndView) {
        modelAndView.setViewName("search/ajaxFragment");
        if (ACCOUNT_SEARCH_TYPE.equals(searchType)) {
            modelAndView.addObject("accounts", searchService.findAccounts(searchQuery, currentPage,
                    TIPS_PER_AJAX_REQUEST));
        } else if (GROUP_SEARCH_TYPE.equals(searchType)) {
            modelAndView.addObject("groups", searchService.findGroups(searchQuery, currentPage,
                    TIPS_PER_AJAX_REQUEST));
        }
        return modelAndView;
    }

    @GetMapping("/search_ajax_pages")
    public ModelAndView searchAjaxPages(@RequestParam("searchQuery") String searchQuery,
                                        @RequestParam("searchType") String searchType,
                                        @RequestParam("currentPage") int currentPage,
                                        ModelAndView modelAndView) {
        modelAndView.setViewName("search/search-results");
        if (ACCOUNT_SEARCH_TYPE.equals(searchType)) {
            modelAndView.addObject("accounts", searchService.findAccounts(searchQuery, currentPage,
                    RESULTS_PER_PAGE));
        } else if (GROUP_SEARCH_TYPE.equals(searchType)) {
            modelAndView.addObject("groups", searchService.findGroups(searchQuery, currentPage,
                    RESULTS_PER_PAGE));
        }
        return modelAndView;
    }

}
