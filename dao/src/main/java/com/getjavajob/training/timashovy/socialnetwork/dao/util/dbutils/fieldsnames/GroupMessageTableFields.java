package com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames;

import sun.jvm.hotspot.utilities.AssertionFailure;

/**
 * Util class contains names of the fields of {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#GROUP_MESSAGE_TABLE group messages table}.
 * Class is not intended to have any instances - private constructor with exception throwing implements this requirement.
 */
public class GroupMessageTableFields {

    public static final String GROUP_MESSAGE_ID = "id";
    public static final String GROUP_MESSAGE_ACCOUNT_AUTHOR_ID = "account_author_id";
    public static final String GROUP_MESSAGE_GROUP_ID = "group_id";
    public static final String GROUP_MESSAGE_MESSAGE_TEXT = "message_text";
    public static final String GROUP_MESSAGE_MESSAGE_IMAGE = "message_image";
    public static final String GROUP_MESSAGE_CREATION_DATE = "creation_date";

    /**
     * Class is not intended to have any instances. No one should call constructor even within class itself.
     *
     * @throws AssertionError when someone tries to call constructor
     */
    private GroupMessageTableFields() {
        throw new AssertionFailure();
    }

}
