package com.getjavajob.training.timashovy.socialnetwork.web.mappers;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.AccountDto;

import java.io.IOException;

public class AccountMapper {

    public Account toAccount(AccountDto accountDto) throws IOException {
        return new Account.Builder()
                .firstName(accountDto.getFirstName())
                .lastName(accountDto.getLastName())
                .middleName(accountDto.getMiddleName())
                .email(accountDto.getEmail())
                .icq(accountDto.getIcq())
                .skype(accountDto.getSkype())
                .avatar(accountDto.getAvatar() != null ? accountDto.getAvatar().getInputStream() : null)
                .build();
    }

}
