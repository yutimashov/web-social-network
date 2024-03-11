package com.getjavajob.training.timashovy.socialnetwork.common.account;

public class Password {

    private Long accountId;
    private String password;
    private String salt;

    public Password(String password, String salt) {
        this.password = password;
        this.salt = salt;
    }

    public Password(Long accountId, String password, String salt) {
        this(password, salt);
        this.accountId = accountId;
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

}
