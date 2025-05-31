package com.getjavajob.searchservice.web.controller;

import com.getjavajob.searchservice.service.account.AccountSearchService;
import com.getjavajob.searchservice.service.group.GroupSearchService;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static java.util.Collections.emptyList;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final AccountSearchService accountSearchService;
    private final GroupSearchService groupSearchService;

    public SearchController(AccountSearchService accountSearchService, GroupSearchService groupSearchService) {
        this.accountSearchService = accountSearchService;
        this.groupSearchService = groupSearchService;
    }

    @GetMapping
    public String doGet(@RequestParam("searchQuery") String searchQuery,
                        @RequestParam("searchType") String searchType,
                        @RequestParam(required = false, defaultValue = "false") boolean isAjax,
                        @RequestParam(defaultValue = "100") int limit,
                        @RequestParam(required = false, defaultValue = "0") Long lastAccountId,
                        @RequestParam(required = false, defaultValue = "0") String lastGroupName,
                        Model model) {
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("searchType", searchType);
        return "account".equals(searchType) ? handleAccountSearch(model, lastAccountId, searchQuery, limit, isAjax)
                : handleGroupSearch(model, searchQuery, lastGroupName, limit, isAjax);
    }

    private String handleAccountSearch(Model model, Long lastId, String searchQuery, int limit, boolean isAjax) {
        List<Account> accountsBatch = accountSearchService.findAccounts(searchQuery, lastId, limit);
        if (!accountsBatch.isEmpty()) {
            Long newLastId = accountsBatch.stream()
                    .map(Account::getId)
                    .max(Long::compareTo)
                    .orElse(lastId);
            model.addAttribute("accounts", accountsBatch);
            model.addAttribute("lastAccountId", newLastId);
            model.addAttribute("limit", limit);
        } else {
            model.addAttribute("searchResults", emptyList());
            model.addAttribute("hasMore", false);
        }
        return !isAjax ? "search/result" : "search/search-results-accounts";
    }

    private String handleGroupSearch(Model model, String searchQuery, String lastName, int limit, boolean isAjax) {
        List<Group> groupsBatch = groupSearchService.findAccounts(searchQuery, lastName, limit);
        if (!groupsBatch.isEmpty()) {
            String newLastName = groupsBatch.stream()
                    .map(Group::getName)
                    .max(String::compareTo)
                    .orElse(lastName);
            model.addAttribute("searchResults", groupsBatch);
            model.addAttribute("lastGroupName", newLastName);
            model.addAttribute("limit", limit);
        } else {
            model.addAttribute("searchResults", emptyList());
            model.addAttribute("hasMore", false);
        }
        return !isAjax ? "search/result" : "search/result-account-ajaxFragment";
    }

}
