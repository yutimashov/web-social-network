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
            WHERE (a.first_name ILIKE '%' || :searchQuery || '%'
                OR a.last_name ILIKE '%' || :searchQuery || '%')
                AND (a.first_name, a.last_name) > (:lastFirstName, :lastLastName)
            ORDER BY a.first_name
            LIMIT :limit
            """, nativeQuery = true)
    List<Account> findAccountsSearchResult(
            @Param("searchQuery") String searchQuery,
            @Param("lastFirstName") String lastFirstName,
            @Param("lastLastName") String lastLastName,
            @Param("limit") int limit);

}
