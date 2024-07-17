package com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames;

/**
 * Util class contains names of the fields of {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#PERSONAL_WALL_MESSAGE_TABLE personal wall messages table}.
 * Class is not intended to have any instances - private constructor with exception throwing implements this requirement.
 */
public class PersonalWallMessagesTableFields {

    public static final String PERSONAL_WALL_MESSAGE_ID = "id";
    public static final String PERSONAL_WALL_MESSAGE_AUTHOR_ID = "account_author_id";
    public static final String PERSONAL_WALL_MESSAGE_RECEIVER_ID = "account_receiver_id";
    public static final String PERSONAL_WALL_MESSAGE_TEXT = "message_text";
    public static final String PERSONAL_WALL_MESSAGE_IMAGE = "message_image";
    public static final String PERSONAL_WALL_MESSAGE_CREATION_DATE = "creation_date";

    /**
     * Class is not intended to have any instances. No one should call constructor even within class itself.
     *
     * @throws AssertionError when someone tries to call constructor
     */
    private PersonalWallMessagesTableFields() {
        throw new AssertionError();
    }

}
