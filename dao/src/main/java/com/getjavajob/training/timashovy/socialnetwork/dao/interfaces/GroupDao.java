package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;

import java.util.List;

public interface GroupDao extends BaseDao<Group> {

    boolean sendGroupMemberRequest(Long groupId, Long accountId);

    boolean makeUserGroupAdmin(Long groupId, Long accountId);

    boolean makeAccountGroupMember(Long groupId, Long accountId);

    List<Long> getIncomingGroupRequests(Long groupId);

    boolean deleteGroupMember(Long groupId, Long accountId);

    boolean isAccountAdmin(Long groupId, Long accountId);

    boolean isAccountGroupSubscriber(Long groupId, Long accountId);

    boolean isAccountGroupMember(Long groupId, Long accountId);

    List<Long> getGroupRegularMembers(Long groupId);

    List<Long> getGroupAdmins(Long groupId);

}
