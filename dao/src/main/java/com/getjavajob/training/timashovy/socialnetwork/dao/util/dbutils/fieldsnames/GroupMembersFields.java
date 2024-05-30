package com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames;

/**
 * Util class contains names of the fields of {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#GROUP_MEMBERS_TABLE group members table}.
 * Class is not intended to have any instances - private constructor with exception throwing implements this requirement.
 */
public class GroupMembersFields {

    public static final String GROUP_MEMBERS_ID = "id";
    public static final String GROUP_MEMBERS_ACCOUNT_ID = "account_id";
    public static final String GROUP_MEMBERS_GROUP_ID = "group_id";
    public static final String GROUP_MEMBERS_IS_ADMIN = "is_admin";
    public static final String GROUP_MEMBERS_IS_MEMBER = "is_member";
    public static final String GROUP_MEMBERS_REGISTRATION_DATE = "registration_date";

    /**
     * Class is not intended to have any instances. No one should call constructor even within class itself.
     *
     * @throws AssertionError when someone tries to call constructor
     */
    private GroupMembersFields() {
        throw new AssertionError();
    }

}
