package com.getjavajob.training.timashovy.socialnetwork.common.account;

public class AuthToken {

    private Long id;
    private String token;
    private String validator;
    private Long accountId;

    public AuthToken(String token, String validator, Long accountId) {
        this.token = token;
        this.validator = validator;
        this.accountId = accountId;
    }

    public AuthToken(Long id, String token, String validator, Long accountId) {
        this(token, validator, accountId);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

}
