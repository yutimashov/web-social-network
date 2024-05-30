package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AdminService;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.AccountRole.ADMIN;

public class AdminServiceImpl implements AdminService {

    private final AccountService accountService;
    private static volatile AdminService instance;

    private AdminServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    public static AdminService getInstance(AccountService accountService) {
        if (instance == null) {
            synchronized (AdminServiceImpl.class) {
                if (instance == null) {
                    instance = new AdminServiceImpl(accountService);
                }
            }
        }
        return instance;
    }

    @Override
    public void makeAdmin(Long accountId) {
        accountService.updateRole(accountId, ADMIN);
    }

}
