package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.search;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import java.util.List;

public interface AccountSearchRepository {

    List<Account> findResults(String searchQuery, String lastFirstName, String lastLastName, int limit);

}
