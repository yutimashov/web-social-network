package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories.account;

import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import org.springframework.data.repository.CrudRepository;

public interface PasswordRepositorySpringData extends CrudRepository<Password, Long> {
}
