package com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames;

public class PasswordTableFields {

    public static final String PASSWORD_ID = "id";
    public static final String PASSWORD_ACCOUNT_ID = "account_id";
    public static final String PASSWORD_HASH = "hash_password";
    public static final String PASSWORD_SALT = "salt";

    private PasswordTableFields() {
        throw new AssertionError();
    }

}
