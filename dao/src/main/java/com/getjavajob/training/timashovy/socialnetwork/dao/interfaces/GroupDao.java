package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;

import java.util.List;

/**
 * Interface contains all the methods relevant to group functionality
 */
public interface GroupDao extends BaseDao<Group> {

    void sendRequest(Long groupId, Long accountId);

    void makeAdmin(Long groupId, Long accountId);

    void makeMember(Long groupId, Long accountId);

    void deleteMember(Long groupId, Long accountId);

    boolean isAdmin(Long groupId, Long accountId);

    boolean isSubscriber(Long groupId, Long accountId);

    boolean isMember(Long groupId, Long accountId);

    List<Long> getRegularMembers(Long groupId);

    List<Long> getAdmins(Long groupId);

    List<Long> getRequests(Long groupId);

}
