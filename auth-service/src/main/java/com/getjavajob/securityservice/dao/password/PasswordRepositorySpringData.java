package com.getjavajob.securityservice.dao.password;

import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import org.springframework.data.repository.CrudRepository;

public interface PasswordRepositorySpringData extends CrudRepository<Password, Long> {
}
