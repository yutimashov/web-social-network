package com.getjavajob.training.timashovy.socialnetwork.common.account;

import java.util.Objects;

public class Password {

    private Long accountId;
    private String password;
    private String salt;

    public Password(Long accountId, String password, String salt) {
        this.accountId = accountId;
        this.password = password;
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(accountId, password1.accountId) && Objects.equals(password, password1.password)
                && Objects.equals(salt, password1.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, password, salt);
    }

    @Override
    public String toString() {
        return "Password{" +
                "accountId=" + accountId +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }

}
