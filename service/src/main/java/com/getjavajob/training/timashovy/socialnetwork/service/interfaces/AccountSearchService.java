package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import java.util.List;

public interface AccountSearchService {

    List<Account> findAccounts(String searchQuery, Long lastId, int limit);

}
