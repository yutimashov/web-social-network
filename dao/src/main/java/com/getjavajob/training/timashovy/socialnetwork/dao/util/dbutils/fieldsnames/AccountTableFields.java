package com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames;

/**
 * Util class contains names of the fields of {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#ACCOUNTS_TABLE accounts table}.
 * Class is not intended to have any instances - private constructor with exception throwing implements this requirement.
 */
public class AccountTableFields {

    public static final String ACCOUNT_ID = "id";
    public static final String ACCOUNT_FIRST_NAME = "first_name";
    public static final String ACCOUNT_LAST_NAME = "last_name";
    public static final String ACCOUNT_MIDDLE_NAME = "middle_name";
    public static final String ACCOUNT_BIRTH_DATE = "birth_date";
    public static final String ACCOUNT_PERSONAL_ADDRESS = "personal_address";
    public static final String ACCOUNT_WORK_ADDRESS = "work_address";
    public static final String ACCOUNT_EMAIL = "email";
    public static final String ACCOUNT_ICQ = "icq";
    public static final String ACCOUNT_SKYPE = "skype";
    public static final String ACCOUNT_ADDITIONAL_INFO = "additional_info";
    public static final String ACCOUNT_ROLE_TYPE = "role_type";
    public static final String ACCOUNT_AVATAR = "avatar";
    public static final String TOTAL_ACCOUNTS_AMOUNT_ALIAS = "total";

    /**
     * Class is not intended to have any instances. No one should call constructor even within class itself.
     *
     * @throws AssertionError when someone tries to call constructor
     */
    private AccountTableFields() {
        throw new AssertionError();
    }

}
