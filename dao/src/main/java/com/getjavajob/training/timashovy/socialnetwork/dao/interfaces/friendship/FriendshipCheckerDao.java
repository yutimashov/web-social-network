package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

public interface FriendshipCheckerDao {

    boolean checkFriendshipRecordExistence(Account requester, Account accepter);

    boolean checkUsersAreFriends(Account requester, Account accepter);

}
