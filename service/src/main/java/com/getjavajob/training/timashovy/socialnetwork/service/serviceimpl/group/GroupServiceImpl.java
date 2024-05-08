package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group.GroupDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.GroupDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.AccountServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroupServiceImpl implements GroupService {

    private static final GroupServiceImpl GROUP_SERVICE_INSTANCE = new GroupServiceImpl();

    private final GroupDao groupDaoInstance = GroupDaoImpl.getInstance();
    private final AccountService accountService = AccountServiceImpl.getInstance();

    private GroupServiceImpl() {
    }

    public static GroupServiceImpl getInstance() {
        return GROUP_SERVICE_INSTANCE;
    }

    @Override
    public Long create(Group group) {
        return groupDaoInstance.create(group);
    }

    @Override
    public List<Group> getAll() {
        return groupDaoInstance.getAll();
    }

    @Override
    public void sendRequest(Long groupId, Long accountId) {
        groupDaoInstance.sendRequest(groupId, accountId);
    }

    @Override
    public void makeAdmin(Long groupId, Long accountId) {
        groupDaoInstance.makeAdmin(groupId, accountId);
    }

    @Override
    public void makeMember(Long groupId, Long accountId) {
        groupDaoInstance.makeMember(groupId, accountId);
    }

    @Override
    public List<Account> getIncomingRequests(Long groupId) {
        List<Long> accountsId = groupDaoInstance.getRequests(groupId);
        List<Account> accounts = new ArrayList<>();
        AccountService accountService = AccountServiceImpl.getInstance();
        for (Long accountId : accountsId) {
            if (accountService.getById(accountId).isPresent()) {
                accounts.add(accountService.getById(accountId).get());
            }
        }
        return accounts;
    }

    @Override
    public void deleteMember(Long groupId, Long accountId) {
        groupDaoInstance.deleteMember(groupId, accountId);
    }

    @Override
    public boolean isAdmin(Long groupId, Long accountId) {
        return groupDaoInstance.isAdmin(groupId, accountId);
    }

    @Override
    public boolean isSubscriber(Long groupId, Long accountId) {
        return groupDaoInstance.isSubscriber(groupId, accountId);
    }

    @Override
    public boolean isMember(Long groupId, Long accountId) {
        return groupDaoInstance.isMember(groupId, accountId);
    }

    @Override
    public List<Account> getRegularMembers(Long groupId) {
        List<Long> accountsId = groupDaoInstance.getRegularMembers(groupId);
        List<Account> groupMembers = new ArrayList<>();
        for (Long accountId : accountsId) {
            if (accountService.getById(accountId).isPresent()) {
                groupMembers.add(accountService.getById(accountId).get());
            }
        }
        return groupMembers;
    }

    @Override
    public List<Account> getAdmins(Long groupId) {
        List<Long> accountsId = groupDaoInstance.getAdmins(groupId);
        List<Account> admins = new ArrayList<>();
        for (Long accountId : accountsId) {
            if (accountService.getById(accountId).isPresent()) {
                admins.add(accountService.getById(accountId).get());
            }
        }
        return admins;
    }

    @Override
    public Optional<Group> getById(Long groupId) {
        return groupDaoInstance.getById(groupId);
    }

}
