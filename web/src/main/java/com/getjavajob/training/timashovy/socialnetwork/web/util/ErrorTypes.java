package com.getjavajob.training.timashovy.socialnetwork.web.util;

public final class ErrorTypes {

    public static final String AUTH_DATA_ERROR = "?error=auth_data";
    public static final String ACCOUNT_REGISTRATION_ERROR = "?error=reg";

    private ErrorTypes() {
        throw new AssertionError();
    }

}
