package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories.search;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountSearchRepositorySpringData extends CrudRepository<Account, Long> {

    @Query(value = """
            SELECT a.*
            FROM account_data.accounts a
            WHERE account_data.similarity(a.fullname, :searchQuery :: text) > 0.3
                AND id > :lastId
            ORDER BY id
            LIMIT :limit
            """, nativeQuery = true)
    List<Account> findAccountsSearchResult(
            @Param("searchQuery") String searchQuery,
            @Param("lastId") Long lastId,
            @Param("limit") int limit);

}
