package com.getjavajob.authservice.dao.account;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepositorySpringData extends CrudRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

}
