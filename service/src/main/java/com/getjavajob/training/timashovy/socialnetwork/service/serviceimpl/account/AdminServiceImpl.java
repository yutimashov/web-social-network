package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AdminService;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.AccountRole.ADMIN;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.AccountServiceImpl.getInstance;

public class AdminServiceImpl implements AdminService {
    private final AccountService accountService;

    private AdminServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    private static class SingletonHolder {

        private static final AdminServiceImpl INSTANCE;

        static {
            INSTANCE = new AdminServiceImpl(getInstance());
        }

    }

    public static AdminServiceImpl getAdminServiceImpl() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void makeAdmin(Long accountId) {
        accountService.updateRole(accountId, ADMIN);
    }

}
