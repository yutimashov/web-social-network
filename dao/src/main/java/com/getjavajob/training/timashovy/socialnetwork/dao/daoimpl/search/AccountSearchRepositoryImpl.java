package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.search;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.search.AccountSearchRepository;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories.search.AccountSearchRepositorySpringData;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Class provides functionality for searching accounts.
 */
@Repository
public class AccountSearchRepositoryImpl implements AccountSearchRepository {

    private final AccountSearchRepositorySpringData accountSearchRepositorySpringData;

    private static final Logger logger = getLogger(AccountSearchRepositoryImpl.class);

    public AccountSearchRepositoryImpl(AccountSearchRepositorySpringData accountSearchRepositorySpringData) {
        this.accountSearchRepositorySpringData = accountSearchRepositorySpringData;
    }

    @Override
    public List<Account> findResults(String searchQuery, String lastFirstName, String lastLastName, int limit) {
        logger.info("Inside AccountSearchRepositoryImpl class");
        return accountSearchRepositorySpringData.findAccountsSearchResult(searchQuery, lastFirstName, lastLastName, limit);
    }

}
