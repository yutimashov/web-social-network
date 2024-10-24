package com.getjavajob.training.timashovy.socialnetwork.web.dto;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

public class AccountRegistrationDataDto {

    private Account account;
    private String password;
    private String personalPhoneNumber;
    private String workPhoneNumber;

    public AccountRegistrationDataDto() {
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonalPhoneNumber() {
        return personalPhoneNumber;
    }

    public void setPersonalPhoneNumber(String personalPhoneNumber) {
        this.personalPhoneNumber = personalPhoneNumber;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

}
