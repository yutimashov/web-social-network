package com.getjavajob.training.timashovy.socialnetwork.web.util;

public final class StatusTypes {

    public static final String AUTH_DATA_ERROR = "?error=auth_data";
    public static final String REG_SUCCESS = "?reg=success";
    public static final String AUTHORIZATION_ERROR = "?error=authorization";
    public static final String DELETE_ACCOUNT_SUCCESS = "?delete_account=success";

    /**
     * Class is not supposed to have any instance.
     * It only provides constants describing types of possible errors.
     */
    private StatusTypes() {
        throw new AssertionError();
    }

}
