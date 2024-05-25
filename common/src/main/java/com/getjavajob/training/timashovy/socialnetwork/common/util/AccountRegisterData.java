package com.getjavajob.training.timashovy.socialnetwork.common.util;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;

public class AccountRegisterData {

    private final Account account;
    private final String password;
    private final String personalPhoneNumber;
    private final String workPhoneNumber;

    private AccountRegisterData(Builder builder) {
        this.account = builder.account;
        this.password = builder.password;
        this.personalPhoneNumber = builder.personalPhoneNumber;
        this.workPhoneNumber = builder.workPhoneNumber;
    }

    public Account getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getPersonalPhoneNumbers() {
        return personalPhoneNumber;
    }

    public String getWorkingPhoneNumbers() {
        return workPhoneNumber;
    }

    public static final class Builder {
        private Account account;
        private String password;
        private String personalPhoneNumber;
        private String workPhoneNumber;

        public Builder account(Account account) {
            this.account = account;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder personalPhoneNumber(String personalPhoneNumber) {
            this.personalPhoneNumber = personalPhoneNumber;
            return this;
        }

        public Builder workPhoneNumber(String workPhoneNumber) {
            this.workPhoneNumber = workPhoneNumber;
            return this;
        }

        public AccountRegisterData build() {
            return new AccountRegisterData(this);
        }

    }

}
