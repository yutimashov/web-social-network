package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories.account;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepositorySpringData extends CrudRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    @Query(value = """
            SELECT a.*
            FROM account_data.accounts a
            WHERE a.id > :lastId
                AND a.id != :accountId
            ORDER BY a.id
            LIMIT :limit
            """, nativeQuery = true)
    List<Account> getAccountsById(
            @Param("accountId") Long accountId,
            @Param("lastId") Long lastId,
            @Param("limit") int limit);

}
