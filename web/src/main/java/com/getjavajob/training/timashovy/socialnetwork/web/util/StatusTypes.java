package com.getjavajob.training.timashovy.socialnetwork.web.util;

public final class StatusTypes {

    public static final String AUTH_DATA_ERROR = "?error=auth_data";
    public static final String REG_SUCCESS = "?reg=success";
    public static final String ACCOUNT_REGISTRATION_ERROR = "?error=reg";

    /**
     * Class is not supposed to have any instance.
     * It only provides constants describing types of possible errors.
     */
    private StatusTypes() {
        throw new AssertionError();
    }

}
