package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;

import java.util.List;

/**
 * Interface contains all the methods relevant to group functionality
 */
public interface GroupMembershipDao {

    void sendRequest(Group group, Account account);

    void makeAdmin(Group group, Account account);

    void makeMember(Group group, Account account);

    void deleteMember(Group group, Account account);

    boolean isAdmin(Group group, Account account);

    boolean isSubscriber(Group group, Account account);

    boolean isMember(Group group, Account account);

    List<Account> getRegularMembers(Group group);

    List<Account> getAdmins(Group group);

    List<Account> getRequestAccounts(Group group);

}
