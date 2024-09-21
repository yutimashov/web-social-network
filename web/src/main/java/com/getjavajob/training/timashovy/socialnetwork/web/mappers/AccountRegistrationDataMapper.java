package com.getjavajob.training.timashovy.socialnetwork.web.mappers;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.util.AccountRegistrationData;

public class AccountRegistrationDataMapper {

    public AccountRegistrationData toAccountRegistrationData(Account account, String password,
                                                             String personalPhoneNumber, String workPhoneNumber) {
        return new AccountRegistrationData.Builder()
                .account(account)
                .password(password)
                .personalPhoneNumber(personalPhoneNumber)
                .workPhoneNumber(workPhoneNumber)
                .build();
    }

}
