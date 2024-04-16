package com.getjavajob.training.timashovy.socialnetwork.dao.util;

public final class TableNames {

    public static final String ACCOUNT_TABLE = "account_data.account";
    public static final String ACCOUNT_AVATARS_TABLE = "account_data.account_avatars";
    public static final String ACCOUNT_PASSWORDS_TABLE = "account_data.account_passwords";
    public static final String ACCOUNT_PHONES_TABLE = "account_data.account_phones";
    public static final String GROUP_TABLE = "group_data.\"group\"";
    public static final String GROUP_MEMBERS_TABLE = "group_data.group_members";

    private TableNames() {
        throw new AssertionError();
    }

}
