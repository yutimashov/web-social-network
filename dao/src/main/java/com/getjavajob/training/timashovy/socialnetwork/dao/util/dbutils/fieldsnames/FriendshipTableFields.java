package com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames;

/**
 * Util class contains names of the fields of {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#FRIENDSHIP_TABLE friendship table}.
 * Class is not intended to have any instances - private constructor with exception throwing implements this requirement.
 */
public class FriendshipTableFields {

    public static final String FRIENDSHIP_ACCOUNT_ID_1 = "id_1";
    public static final String FRIENDSHIP_ACCOUNT_ID_2 = "id_2";
    public static final String FRIENDSHIP_STATUS = "status";
    public static final String FRIENDSHIP_REQUESTER_ID = "requester_id";
    public static final String FRIENDSHIP_ACCEPTER_ID = "accepter_id";

    /**
     * Class is not intended to have any instances. No one should call constructor even within class itself.
     *
     * @throws AssertionError when someone tries to call constructor
     */
    private FriendshipTableFields() {
        throw new AssertionError();
    }

}
