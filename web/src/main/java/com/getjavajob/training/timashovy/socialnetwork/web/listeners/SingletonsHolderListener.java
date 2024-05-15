package com.getjavajob.training.timashovy.socialnetwork.web.listeners;

import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.AccountDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.PhoneDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoSingletonRegistry;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.AccountServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.LoginService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.PasswordServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.util.ServiceSingletonRegistry;
import com.getjavajob.training.timashovy.socialnetwork.service.util.SingletonRegistry;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import static com.getjavajob.training.timashovy.socialnetwork.web.listeners.SingletonsNames.*;

public class SingletonsHolderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        registerDaoSingletons();
        registerServiceSingletons();
    }

    private void registerDaoSingletons() {
        DaoSingletonRegistry daoSingletonRegistry = DaoSingletonRegistry.getDaoRegistryInstance();
        daoSingletonRegistry.registerSingleton(ACCOUNT_DAO_SINGLETON, AccountDaoImpl.getInstance());
        daoSingletonRegistry.registerSingleton(PHONE_DAO_SINGLETON, PhoneDaoImpl.getInstance());
    }

    private void registerServiceSingletons() {
        SingletonRegistry serviceSingletonRegistry = ServiceSingletonRegistry.getInstance();
        serviceSingletonRegistry.registerSingleton(ACCOUNT_SERVICE_SINGLETON, AccountServiceImpl.getInstance());
        serviceSingletonRegistry.registerSingleton(PASSWORD_SERVICE_SINGLETON, PasswordServiceImpl.getInstance());
        LoginService.registerSingleton(AccountServiceImpl.getInstance(), PasswordServiceImpl.getInstance());
    }

}
