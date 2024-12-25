package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.group.GroupMembershipDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMembershipServiceImpl implements GroupMembershipService {

    private final AccountService accountService;
    private final GroupMembershipDao groupMembershipDao;

    public GroupMembershipServiceImpl(AccountService accountService, GroupMembershipDao groupMembershipDao) {
        this.accountService = accountService;
        this.groupMembershipDao = groupMembershipDao;
    }

    @Override
    public void sendRequest(Group group, Account account) {
        groupMembershipDao.sendRequest(group, account);
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
        return groupMembershipDao.getRequestAccounts(groupId);
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
        return groupMembershipDao.getRegularMembers(groupId);
    }

    @Override
    public List<Account> getAdmins(Long groupId) {
        return groupMembershipDao.getAdmins(groupId);
    }

}
