package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group.GroupDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.GroupDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroupServiceImpl implements GroupService {

    private static final GroupServiceImpl GROUP_SERVICE_INSTANCE = new GroupServiceImpl();

    private final GroupDao groupDaoInstance = GroupDaoImpl.getInstance();

    private GroupServiceImpl() {
    }

    public static GroupServiceImpl getInstance() {
        return GROUP_SERVICE_INSTANCE;
    }

    @Override
    public Long createGroup(Group group) {
        return groupDaoInstance.create(group);
    }

    @Override
    public List<Group> listGroups() {
        return groupDaoInstance.getAll();
    }

    @Override
    public boolean sendGroupMemberRequest(Long groupId, Long accountId) {
        return groupDaoInstance.sendGroupMemberRequest(groupId, accountId);
    }

    @Override
    public boolean makeUserGroupAdmin(Long groupId, Long accountId) {
        return groupDaoInstance.makeUserGroupAdmin(groupId, accountId);
    }

    @Override
    public boolean makeAccountGroupMember(Long groupId, Long accountId) {
        return groupDaoInstance.makeAccountGroupMember(groupId, accountId);
    }

    @Override
    public List<Account> getIncomingGroupRequests(Long groupId) {
        List<Long> accountsId = groupDaoInstance.getIncomingGroupRequests(groupId);
        List<Account> accounts = new ArrayList<>();
        AccountService accountService = AccountServiceImpl.getInstance();
        for (Long accountId : accountsId) {
            if (accountService.getAccountById(accountId).isPresent()) {
                accounts.add(accountService.getAccountById(accountId).get());
            }
        }
        return accounts;
    }

    @Override
    public boolean deleteGroupMember(Long groupId, Long accountId) {
        return groupDaoInstance.deleteGroupMember(groupId, accountId);
    }

    @Override
    public boolean isAccountAdmin(Long groupId, Long accountId) {
        return groupDaoInstance.isAccountAdmin(groupId, accountId);
    }

    @Override
    public boolean isAccountGroupSubscriber(Long groupId, Long accountId) {
        return groupDaoInstance.isAccountGroupSubscriber(groupId, accountId);
    }

    @Override
    public boolean isAccountGroupMember(Long groupId, Long accountId) {
        return groupDaoInstance.isAccountGroupMember(groupId, accountId);
    }

    @Override
    public List<Account> getGroupMembers(Long groupId) {
        List<Long> accountsId = groupDaoInstance.getGroupMembers(groupId);
        List<Account> groupMembers = new ArrayList<>();
        AccountService accountService = AccountServiceImpl.getInstance();
        for (Long accountId : accountsId) {
            if (accountService.getAccountById(accountId).isPresent()) {
                groupMembers.add(accountService.getAccountById(accountId).get());
            }
        }
        return groupMembers;
    }

    @Override
    public Optional<Group> getById(Long groupId) {
        return groupDaoInstance.getById(groupId);
    }

}
