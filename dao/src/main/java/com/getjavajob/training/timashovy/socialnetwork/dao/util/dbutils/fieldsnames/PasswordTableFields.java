package com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames;

/**
 * Util class contains names of the fields of {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#ACCOUNT_PASSWORDS_TABLE passwords table}.
 * Class is not intended to have any instances - private constructor with exception throwing implements this requirement.
 */
public final class PasswordTableFields {

    public static final String PASSWORD_ID = "id";
    public static final String PASSWORD_ACCOUNT_ID = "account_id";
    public static final String PASSWORD_HASH = "hash_password";
    public static final String PASSWORD_SALT = "salt";

}
