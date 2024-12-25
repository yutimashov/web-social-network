package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.Repository;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;

import java.util.Optional;

public interface PasswordRepository extends Repository<Long, Password> {

    Optional<Password> findByEmail(String email);

}
