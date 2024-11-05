package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;

import java.util.List;

public interface GroupMembershipService {

    void sendRequest(Group group, Account account);

    void makeAdmin(Long groupId, Long accountId);

    void makeMember(Long groupId, Long accountId);

    void deleteMember(Long groupId, Long accountId);

    boolean isAdmin(Long groupId, Long accountId);

    boolean isSubscriber(Long groupId, Long accountId);

    boolean isMember(Long groupId, Long accountId);

    List<Account> getIncomingRequests(Long groupId);

    List<Account> getRegularMembers(Long groupId);

    List<Account> getAdmins(Long groupId);

}
