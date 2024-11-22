package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.AccountRepository;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AdminService;
import org.springframework.transaction.annotation.Transactional;

import static com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole.ADMIN;
import static java.util.Objects.isNull;

public class AdminServiceImpl implements AdminService {

    private final AccountRepository accountDao;

    public AdminServiceImpl(AccountRepository accountDao) {
        this.accountDao = accountDao;
    }

    @Transactional
    @Override
    public void makeAdmin(Long accountId) {
        validateAccountId(accountId);
        if (accountDao.getById(accountId).isPresent()) {
            accountDao.changeRole(accountId, ADMIN);
        }
    }

    private void validateAccountId(Long accountId) {
        if (isNull(accountId) || accountId <= 0) {
            throw new IllegalArgumentException("Account id should be positive number greater than 0");
        }
    }

}
