package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.group;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;

import java.util.List;

/**
 * Interface contains all the methods relevant to group functionality
 */
public interface GroupMembershipRepository {

    void sendRequest(Group group, Account account);

    void makeAdmin(Long groupId, Long accountId);

    void makeMember(Long groupId, Long accountId);

    void deleteMember(Long groupId, Long accountId);

    boolean isAdmin(Long groupId, Long accountId);

    boolean isSubscriber(Long groupId, Long accountId);

    boolean isMember(Long groupId, Long accountId);

    List<Account> getRegularMembers(Long groupId);

    List<Account> getAdmins(Long groupId);

    List<Account> getRequestAccounts(Long groupId);

}
