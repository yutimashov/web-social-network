package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group;

import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl {

//    private final GroupRepository groupsDao;
//    private final GroupMembershipService groupMembershipService;
//
//    public GroupServiceImpl(GroupMembershipService groupMembershipService,
//                            GroupRepository groupsDao) {
//        this.groupMembershipService = groupMembershipService;
//        this.groupsDao = groupsDao;
//    }
//
//    @Transactional
//    @Override
//    public Group create(Group group, Account account) {
//        Group createdGroup = groupsDao.save(group);
//        groupMembershipService.sendRequest(createdGroup, account);
//        Long accountId = account.getId();
//        groupMembershipService.makeMember(createdGroup.getId(), accountId);
//        groupMembershipService.makeAdmin(createdGroup.getId(), accountId);
//        return createdGroup;
//    }
//
//    @Override
//    public List<Group> findAll() {
//        return groupsDao.findAll();
//    }
//
//    @Override
//    public Optional<Group> findById(Long groupId) {
//        return groupsDao.findById(groupId);
//    }

}
