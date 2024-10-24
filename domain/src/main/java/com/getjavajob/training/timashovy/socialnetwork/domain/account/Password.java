package com.getjavajob.training.timashovy.socialnetwork.domain.account;

import java.util.Objects;

/**
 * Class store information about Account's password.
 * It also provides basic functionality to work with password.
 */
public class Password {

    private Long id;
    private Long accountId;
    private String password;
    private String salt;

    public Password(Long accountId, String password, String salt) {
        this.accountId = accountId;
        this.password = password;
        this.salt = salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return Objects.equals(id, password1.id) && Objects.equals(accountId, password1.accountId)
                & Objects.equals(password, password1.password) && Objects.equals(salt, password1.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, password, salt);
    }

    @Override
    public String toString() {
        return "Password{id=" + id + ", accountId=" + accountId + ", password=" + password + ", salt=" + salt + "}";
    }

}
