package com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils;

/**
 * Util class contains names of tables in DB.
 * Class is not intended to have any instances - private constructor with exception throwing implements this requirement.
 */
public class TableNames {

    public static final String ACCOUNTS_TABLE = "account_data.accounts";
    public static final String ACCOUNT_PASSWORDS_TABLE = "account_data.account_passwords";
    public static final String ACCOUNT_PHONES_TABLE = "account_data.account_phones";
    public static final String GROUPS_TABLE = "group_data.groups";
    public static final String GROUP_MEMBERS_TABLE = "group_data.group_members";
    public static final String GROUP_MESSAGE_TABLE = "message_data.group_messages";
    public static final String PERSONAL_WALL_MESSAGE_TABLE = "message_data.personal_wall_messages";
    public static final String PERSONAL_MESSAGE_TABLE = "message_data.personal_messages";
    public static final String FRIENDSHIP_TABLE = "friend_data.friendship";

    /**
     * Class is not intended to have any instances. No one should call constructor even within class itself.
     *
     * @throws AssertionError when someone tries to call constructor
     */
    private TableNames() {
        throw new AssertionError();
    }

}
