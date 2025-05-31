package com.getjavajob.searchservice.dao.account;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class provides functionality for searching accounts.
 */
@Repository
public class AccountSearchRepositoryImpl implements AccountSearchRepository {

    private final AccountSearchRepositorySpringData accountSearchRepositorySpringData;

    public AccountSearchRepositoryImpl(AccountSearchRepositorySpringData accountSearchRepositorySpringData) {
        this.accountSearchRepositorySpringData = accountSearchRepositorySpringData;
    }

    @Override
    public List<Account> findResults(String searchQuery, Long lastId, int limit) {
        return accountSearchRepositorySpringData.findAccountsSearchResult(searchQuery, lastId, limit);
    }

}
