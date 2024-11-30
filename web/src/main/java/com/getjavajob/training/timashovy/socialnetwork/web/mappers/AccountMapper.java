package com.getjavajob.training.timashovy.socialnetwork.web.mappers;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.AccountDto;
import com.getjavajob.training.timashovy.socialnetwork.web.util.exceptions.WebException;

import java.io.IOException;

import static java.time.LocalDate.parse;
import static java.util.Objects.isNull;

public class AccountMapper {

    public Account toAccount(AccountDto accountDto) {
        try {
            return new Account.Builder()
                    .firstName(accountDto.getFirstName())
                    .lastName(accountDto.getLastName())
                    .middleName(accountDto.getMiddleName())
                    .email(accountDto.getEmail())
                    .icq(accountDto.getIcq())
                    .skype(accountDto.getSkype())
                    .avatar(!isNull(accountDto.getAvatar()) && accountDto.getAvatar().getSize() > 0
                            ? accountDto.getAvatar().getBytes() : null)
                    .birthDate(!isNull(accountDto.getBirthDate()) && !accountDto.getBirthDate().isEmpty()
                            ? parse(accountDto.getBirthDate()) : null)
                    .build();
        } catch (IOException e) {
            throw new WebException(e.getMessage(), e.getCause());
        }
    }

}
