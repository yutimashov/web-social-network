package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AdminService;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.Role.ADMIN;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.AccountServiceImpl.getInstance;

public class AdminServiceImpl implements AdminService {

    private static final AdminServiceImpl ADMIN_SERVICE = new AdminServiceImpl();
    private final AccountService accountService = getInstance();

    private AdminServiceImpl() {
    }

    public static AdminServiceImpl getAdminServiceImpl() {
        return ADMIN_SERVICE;
    }

    @Override
    public void makeAdmin(Long accountId) {
        accountService.updateAccountRole(accountId, ADMIN);
    }

}
