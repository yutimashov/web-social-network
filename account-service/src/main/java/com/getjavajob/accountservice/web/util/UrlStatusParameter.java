package com.getjavajob.accountservice.web.util;

public enum UrlStatusParameter {

    AUTH_DATA_ERROR("?error=auth_data"),
    REG_SUCCESS("?reg=success"),
    AUTHORIZATION_ERROR("?error=authorization"),
    DELETE_ACCOUNT_SUCCESS_STATUS("?delete_account=success");

    private final String value;

    UrlStatusParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
