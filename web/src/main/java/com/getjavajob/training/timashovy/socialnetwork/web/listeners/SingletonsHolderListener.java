package com.getjavajob.training.timashovy.socialnetwork.web.listeners;

import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.AccountDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.PasswordDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.PhoneDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship.FriendshipCheckerDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship.FriendshipDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group.GroupDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group.GroupMembershipDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message.GroupMessageDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message.PersonalMessageDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message.PersonalWallMessageDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.search.SearchAccountDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.search.SearchGroupDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.TransactionManager;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.singletonsregistry.DaoSingletonRegistry;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.*;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group.GroupMembershipServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group.GroupServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.search.SearchServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonRegistry;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.singletonsregistry.DaoSingletonNames.*;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.*;

public class SingletonsHolderListener implements ServletContextListener {

    private final TransactionManager transactionManager = TransactionManager.getInstance();
    private final DaoSingletonRegistry daoSingletonRegistry = DaoSingletonRegistry.getInstance();
    private final ServiceSingletonRegistry serviceSingletonRegistry = ServiceSingletonRegistry.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        registerDaoSingletons();
        registerServiceSingletons();
    }

    private void registerDaoSingletons() {
        daoSingletonRegistry.addSingleton(PHONE_DAO_SINGLETON, PhoneDaoImpl.getInstance(transactionManager));
        daoSingletonRegistry.addSingleton(ACCOUNT_DAO_SINGLETON, AccountDaoImpl.getInstance(
                daoSingletonRegistry.getSingleton(PHONE_DAO_SINGLETON), transactionManager));
        daoSingletonRegistry.addSingleton(FRIENDSHIP_DAO_SINGLETON, FriendshipDaoImpl.getInstance());
        daoSingletonRegistry.addSingleton(FRIENDSHIP_DAO_CHECKER_SINGLETON, FriendshipCheckerDaoImpl.getInstance());
        daoSingletonRegistry.addSingleton(PASSWORD_DAO_SINGLETON, PasswordDaoImpl.getInstance(transactionManager));
        daoSingletonRegistry.addSingleton(GROUP_DAO_SINGLETON, GroupDaoImpl.getInstance());
        daoSingletonRegistry.addSingleton(GROUP_MEMBERSHIP_DAO_SINGLETON, GroupMembershipDaoImpl.getInstance());
        daoSingletonRegistry.addSingleton(GROUP_MESSAGE_DAO_SINGLETON, GroupMessageDaoImpl.getInstance());
        daoSingletonRegistry.addSingleton(PERSONAL_MESSAGE_DAO_SINGLETON, PersonalMessageDaoImpl.getInstance());
        daoSingletonRegistry.addSingleton(PERSONAL_WALL_MESSAGE_DAO_SINGLETON, PersonalWallMessageDaoImpl.getInstance());
        daoSingletonRegistry.addSingleton(SEARCH_ACCOUNT_DAO_SINGLETON, SearchAccountDaoImpl.getInstance());
        daoSingletonRegistry.addSingleton(SEARCH_GROUP_DAO_SINGLETON, SearchGroupDaoImpl.getInstance());
    }

    private void registerServiceSingletons() {
        serviceSingletonRegistry.addSingleton(PASSWORD_SERVICE_SINGLETON, PasswordServiceImpl.getInstance(
                daoSingletonRegistry.getSingleton(PASSWORD_DAO_SINGLETON)
        ));
        serviceSingletonRegistry.addSingleton(PHONE_SERVICE_SINGLETON, PhoneServiceImpl.getInstance(
                daoSingletonRegistry.getSingleton(PHONE_DAO_SINGLETON)
        ));
        serviceSingletonRegistry.addSingleton(ACCOUNT_SERVICE_SINGLETON, AccountServiceImpl.getInstance(
                daoSingletonRegistry.getSingleton(ACCOUNT_DAO_SINGLETON),
                daoSingletonRegistry.getSingleton(FRIENDSHIP_DAO_SINGLETON),
                daoSingletonRegistry.getSingleton(FRIENDSHIP_DAO_CHECKER_SINGLETON),
                serviceSingletonRegistry.getSingleton(PHONE_SERVICE_SINGLETON),
                daoSingletonRegistry.getSingleton(PHONE_DAO_SINGLETON),
                serviceSingletonRegistry.getSingleton(PASSWORD_SERVICE_SINGLETON),
                daoSingletonRegistry.getSingleton(PASSWORD_DAO_SINGLETON), transactionManager
        ));
        serviceSingletonRegistry.addSingleton(LOGIN_SERVICE_SINGLETON, LoginServiceImpl.getInstance(
                serviceSingletonRegistry.getSingleton(ACCOUNT_SERVICE_SINGLETON),
                serviceSingletonRegistry.getSingleton(PASSWORD_SERVICE_SINGLETON)
        ));
        serviceSingletonRegistry.addSingleton(ADMIN_SERVICE_SINGLETON, AdminServiceImpl.getInstance(
                serviceSingletonRegistry.getSingleton(ACCOUNT_SERVICE_SINGLETON)
        ));
        serviceSingletonRegistry.addSingleton(MESSAGE_SERVICE_SINGLETON, MessageServiceImpl.getInstance(
                daoSingletonRegistry.getSingleton(GROUP_MESSAGE_DAO_SINGLETON),
                daoSingletonRegistry.getSingleton(PERSONAL_WALL_MESSAGE_DAO_SINGLETON),
                daoSingletonRegistry.getSingleton(PERSONAL_MESSAGE_DAO_SINGLETON),
                serviceSingletonRegistry.getSingleton(ACCOUNT_SERVICE_SINGLETON)
        ));

        serviceSingletonRegistry.addSingleton(GROUP_SERVICE_SINGLETON, GroupServiceImpl.getInstance(
                daoSingletonRegistry.getSingleton(GROUP_DAO_SINGLETON)
        ));
        serviceSingletonRegistry.addSingleton(GROUP_MEMBERSHIP_SERVICE_SINGLETON,
                GroupMembershipServiceImpl.getInstance(
                        serviceSingletonRegistry.getSingleton(ACCOUNT_SERVICE_SINGLETON),
                        daoSingletonRegistry.getSingleton(GROUP_MEMBERSHIP_DAO_SINGLETON)
                ));
        serviceSingletonRegistry.addSingleton(SEARCH_SERVICE_SINGLETON, SearchServiceImpl.getInstance(
                daoSingletonRegistry.getSingleton(SEARCH_ACCOUNT_DAO_SINGLETON),
                daoSingletonRegistry.getSingleton(SEARCH_GROUP_DAO_SINGLETON)
        ));
    }

}
