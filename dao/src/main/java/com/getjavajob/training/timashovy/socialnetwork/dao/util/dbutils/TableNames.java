package com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils;

public final class TableNames {

    public static final String ACCOUNT_TABLE = "account_data.accounts";
    public static final String ACCOUNT_PASSWORDS_TABLE = "account_data.account_passwords";
    public static final String ACCOUNT_PHONES_TABLE = "account_data.account_phones";
    public static final String GROUP_TABLE = "group_data.groups";
    public static final String GROUP_MEMBERS_TABLE = "group_data.group_members";
    public static final String GROUP_MESSAGE_TABLE = "message_data.group_messages";
    public static final String PERSONAL_WALL_MESSAGE_TABLE = "message_data.personal_wall_messages";
    public static final String PERSONAL_MESSAGE_TABLE = "message_data.personal_messages";
    public static final String FRIENDSHIP_TABLE = "friend_data.friendship";

    private TableNames() {
        throw new AssertionError();
    }

}
