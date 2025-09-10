package com.getjavajob.searchservice.service.account;

import com.getjavajob.searchservice.dao.account.AccountSearchRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountSearchServiceImpl implements AccountSearchService {

    private final AccountSearchRepository searchAccountRepository;

    public AccountSearchServiceImpl(AccountSearchRepository searchAccountRepository) {
        this.searchAccountRepository = searchAccountRepository;
    }

    @Override
    public List<Account> findAccounts(String searchQuery, Long lastId, int limit) {
        return searchAccountRepository.findResults(searchQuery, lastId, limit);
    }

}
