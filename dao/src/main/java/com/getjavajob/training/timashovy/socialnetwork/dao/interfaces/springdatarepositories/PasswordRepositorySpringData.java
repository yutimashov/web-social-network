package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories;

import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PasswordRepositorySpringData extends CrudRepository<Password, Long> {

    Optional<Password> findByAccountEmail(String email);

}
