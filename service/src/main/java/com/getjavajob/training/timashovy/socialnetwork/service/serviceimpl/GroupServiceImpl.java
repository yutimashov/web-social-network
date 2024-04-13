package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.AccountGroupDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.GroupDaoImpl.getGroupDaoInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl.getAccountServiceInstance;

public class GroupServiceImpl implements GroupService {

    private static final GroupServiceImpl GROUP_SERVICE_INSTANCE = new GroupServiceImpl();

    private final AccountGroupDao<Group> groupDaoInstance = getGroupDaoInstance();

    private GroupServiceImpl() {
    }

    public static GroupServiceImpl getGroupServiceImplInstance() {
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
        AccountService accountService = getAccountServiceInstance();
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
    public boolean isAccountAdmin(Long accountId) {
        return groupDaoInstance.isAccountAdmin(accountId);
    }

    @Override
    public Optional<Group> getById(Long groupId) {
        return groupDaoInstance.getById(groupId);
    }

}
