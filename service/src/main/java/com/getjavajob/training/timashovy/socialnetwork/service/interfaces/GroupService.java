package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    Long create(Group group);

    List<Group> getAll();

    void sendRequest(Long groupId, Long accountId);

    void makeAdmin(Long groupId, Long accountId);

    Optional<Group> getById(Long groupId);

    void makeMember(Long groupId, Long accountId);

    List<Account> getIncomingRequests(Long groupId);

    void deleteMember(Long groupId, Long accountId);

    boolean isAdmin(Long groupId, Long accountId);

    boolean isSubscriber(Long groupId, Long accountId);

    boolean isMember(Long groupId, Long accountId);

    List<Account> getRegularMembers(Long groupId);

    List<Account> getAdmins(Long groupId);

}
