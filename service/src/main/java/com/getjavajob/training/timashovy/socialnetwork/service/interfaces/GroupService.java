package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    Long createGroup(Group group);
    List<Group> listGroups();
    boolean sendGroupMemberRequest(Long groupId, Long accountId);
    boolean makeUserGroupAdmin(Long groupId, Long accountId);
    Optional<Group> getById(Long groupId);
    boolean makeAccountGroupMember(Long groupId, Long accountId);
    List<Account> getIncomingGroupRequests(Long groupId);
    boolean deleteGroupMember(Long groupId, Long accountId);
    boolean isAccountAdmin(Long accountId);
    boolean isAccountGroupSubscriber(Long groupId, Long accountId);

    boolean isAccountGroupMember(Long groupId, Long accountId);

}
