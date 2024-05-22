package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.GroupMembershipDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;

import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonRegistry.getServiceSingletonRegistry;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.ACCOUNT_SERVICE_SINGLETON;

public class GroupMembershipServiceImpl implements GroupMembershipService {

    private final AccountService accountService;
    private final GroupMembershipDao groupMembershipDao;

    private GroupMembershipServiceImpl(AccountService accountService, GroupMembershipDao groupMembershipDao) {
        this.accountService = accountService;
        this.groupMembershipDao = groupMembershipDao;
    }

    public static GroupMembershipService createInstance(AccountService accountService,
                                                            GroupMembershipDao groupMembershipDao) {
        return new GroupMembershipServiceImpl(accountService, groupMembershipDao);
    }

    @Override
    public void sendRequest(Long groupId, Long accountId) {
        groupMembershipDao.sendRequest(groupId, accountId);
    }

    @Override
    public void makeAdmin(Long groupId, Long accountId) {
        groupMembershipDao.makeAdmin(groupId, accountId);
    }

    @Override
    public void makeMember(Long groupId, Long accountId) {
        groupMembershipDao.makeMember(groupId, accountId);
    }

    @Override
    public List<Account> getIncomingRequests(Long groupId) {
        List<Long> accountsId = groupMembershipDao.getRequests(groupId);
        List<Account> accounts = new ArrayList<>();
        AccountService accountService = getServiceSingletonRegistry().getSingleton(ACCOUNT_SERVICE_SINGLETON);
        for (Long accountId : accountsId) {
            if (accountService.getById(accountId).isPresent()) {
                accounts.add(accountService.getById(accountId).get());
            }
        }
        return accounts;
    }

    @Override
    public void deleteMember(Long groupId, Long accountId) {
        groupMembershipDao.deleteMember(groupId, accountId);
    }

    @Override
    public boolean isAdmin(Long groupId, Long accountId) {
        return groupMembershipDao.isAdmin(groupId, accountId);
    }

    @Override
    public boolean isSubscriber(Long groupId, Long accountId) {
        return groupMembershipDao.isSubscriber(groupId, accountId);
    }

    @Override
    public boolean isMember(Long groupId, Long accountId) {
        return groupMembershipDao.isMember(groupId, accountId);
    }

    @Override
    public List<Account> getRegularMembers(Long groupId) {
        List<Long> accountsId = groupMembershipDao.getRegularMembers(groupId);
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
        List<Long> accountsId = groupMembershipDao.getAdmins(groupId);
        List<Account> admins = new ArrayList<>();
        for (Long accountId : accountsId) {
            if (accountService.getById(accountId).isPresent()) {
                admins.add(accountService.getById(accountId).get());
            }
        }
        return admins;
    }

}
