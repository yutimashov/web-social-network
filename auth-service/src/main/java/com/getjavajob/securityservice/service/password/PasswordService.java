package com.getjavajob.securityservice.service.password;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import com.getjavajob.training.timashovy.socialnetwork.dto.password.PasswordDTO;

public interface PasswordService {

    Password create(Account account, String rawPassword);

    PasswordDTO get(Long accountId);

}
