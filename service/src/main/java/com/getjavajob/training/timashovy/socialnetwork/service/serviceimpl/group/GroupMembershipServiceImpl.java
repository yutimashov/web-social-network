package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.group.GroupMembershipRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMembershipServiceImpl implements GroupMembershipService {

    private final AccountService accountService;
    private final GroupMembershipRepository groupMembershipRepository;

    public GroupMembershipServiceImpl(AccountService accountService, GroupMembershipRepository groupMembershipRepository) {
        this.accountService = accountService;
        this.groupMembershipRepository = groupMembershipRepository;
    }

    @Override
    public void sendRequest(Group group, Account account) {
        groupMembershipRepository.sendRequest(group, account);
    }

    @Override
    public void makeAdmin(Long groupId, Long accountId) {
        groupMembershipRepository.makeAdmin(groupId, accountId);
    }

    @Override
    public void makeMember(Long groupId, Long accountId) {
        groupMembershipRepository.makeMember(groupId, accountId);
    }

    @Override
    public List<Account> getIncomingRequests(Long groupId) {
        return groupMembershipRepository.getRequestAccounts(groupId);
    }

    @Override
    public void deleteMember(Long groupId, Long accountId) {
        groupMembershipRepository.deleteMember(groupId, accountId);
    }

    @Override
    public boolean isAdmin(Long groupId, Long accountId) {
        return groupMembershipRepository.isAdmin(groupId, accountId);
    }

    @Override
    public boolean isSubscriber(Long groupId, Long accountId) {
        return groupMembershipRepository.isSubscriber(groupId, accountId);
    }

    @Override
    public boolean isMember(Long groupId, Long accountId) {
        return groupMembershipRepository.isMember(groupId, accountId);
    }

    @Override
    public List<Account> getRegularMembers(Long groupId) {
        return groupMembershipRepository.getRegularMembers(groupId);
    }

    @Override
    public List<Account> getAdmins(Long groupId) {
        return groupMembershipRepository.getAdmins(groupId);
    }

}
