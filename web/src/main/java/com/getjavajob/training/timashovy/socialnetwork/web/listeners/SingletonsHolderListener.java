package com.getjavajob.training.timashovy.socialnetwork.web.listeners;

import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.AccountDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.PasswordDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.PhoneDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship.FriendshipCheckerImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship.FriendshipDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group.GroupAvatarDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group.GroupDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message.GroupMessageDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message.PersonalMessageDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message.PersonalWallMessageDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.search.SearchAccountDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.search.SearchGroupDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.singletonsregistry.DaoSingletonRegistry;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.*;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group.GroupAvatarServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group.GroupServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.search.SearchServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.singletonsregistry.SingletonRegistry;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.singletonsregistry.DaoSingletonNames.*;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.singletonsregistry.DaoSingletonRegistry.getDaoRegistryInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonRegistry.getServiceSingletonRegistry;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.*;

public class SingletonsHolderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        registerDaoSingletons();
        registerServiceSingletons();
    }

    private void registerDaoSingletons() {
        DaoSingletonRegistry daoSingletonRegistry = getDaoRegistryInstance();
        daoSingletonRegistry.registerSingleton(PHONE_DAO_SINGLETON, PhoneDaoImpl.createInstance());
        daoSingletonRegistry.registerSingleton(ACCOUNT_DAO_SINGLETON, AccountDaoImpl.createInstance(
                getDaoRegistryInstance().getSingleton(PHONE_DAO_SINGLETON)));
        daoSingletonRegistry.registerSingleton(FRIENDSHIP_DAO_SINGLETON, FriendshipDaoImpl.createInstance());
        daoSingletonRegistry.registerSingleton(FRIENDSHIP_CHECKER_SINGLETON, FriendshipCheckerImpl.createInstance());
        daoSingletonRegistry.registerSingleton(PASSWORD_DAO_SINGLETON, PasswordDaoImpl.createInstance());
        daoSingletonRegistry.registerSingleton(GROUP_AVATAR_SINGLETON, GroupAvatarDaoImpl.createInstance());
        daoSingletonRegistry.registerSingleton(GROUP_DAO_SINGLETON, GroupDaoImpl.createInstance());
        daoSingletonRegistry.registerSingleton(GROUP_MESSAGE_DAO_SINGLETON, GroupMessageDaoImpl.createInstance());
        daoSingletonRegistry.registerSingleton(PERSONAL_MESSAGE_DAO_SINGLETON, PersonalMessageDaoImpl.createInstance());
        daoSingletonRegistry.registerSingleton(PERSONAL_WALL_MESSAGE_DAO_SINGLETON, PersonalWallMessageDaoImpl.createInstance());
        daoSingletonRegistry.registerSingleton(SEARCH_ACCOUNT_DAO_SINGLETON, SearchAccountDaoImpl.createInstance());
        daoSingletonRegistry.registerSingleton(SEARCH_GROUP_DAO_SINGLETON, SearchGroupDaoImpl.createInstance());
    }

    private void registerServiceSingletons() {
        SingletonRegistry serviceSingletonRegistry = getServiceSingletonRegistry();
        serviceSingletonRegistry.registerSingleton(ACCOUNT_SERVICE_SINGLETON, AccountServiceImpl.createInstance(
                getDaoRegistryInstance().getSingleton(ACCOUNT_DAO_SINGLETON),
                getDaoRegistryInstance().getSingleton(FRIENDSHIP_DAO_SINGLETON),
                getDaoRegistryInstance().getSingleton(FRIENDSHIP_CHECKER_SINGLETON),
                getDaoRegistryInstance().getSingleton(PHONE_DAO_SINGLETON)
        ));
        serviceSingletonRegistry.registerSingleton(PASSWORD_SERVICE_SINGLETON, PasswordServiceImpl.createInstance(
                getDaoRegistryInstance().getSingleton(PASSWORD_DAO_SINGLETON)
        ));
        serviceSingletonRegistry.registerSingleton(LOGIN_SERVICE_SINGLETON, LoginServiceImpl.createInstance(
                getServiceSingletonRegistry().getSingleton(ACCOUNT_SERVICE_SINGLETON),
                getServiceSingletonRegistry().getSingleton(PASSWORD_SERVICE_SINGLETON)
        ));
        serviceSingletonRegistry.registerSingleton(ADMIN_SERVICE_SINGLETON, AdminServiceImpl.createInstance(
                getServiceSingletonRegistry().getSingleton(ACCOUNT_SERVICE_SINGLETON)
        ));
        serviceSingletonRegistry.registerSingleton(MESSAGE_SERVICE_SINGLETON, MessageServiceImpl.createInstance(
                getDaoRegistryInstance().getSingleton(GROUP_MESSAGE_DAO_SINGLETON),
                getDaoRegistryInstance().getSingleton(PERSONAL_WALL_MESSAGE_DAO_SINGLETON),
                getDaoRegistryInstance().getSingleton(PERSONAL_MESSAGE_DAO_SINGLETON),
                getServiceSingletonRegistry().getSingleton(ACCOUNT_SERVICE_SINGLETON)
        ));
        serviceSingletonRegistry.registerSingleton(PHONE_SERVICE_SINGLETON, PhoneServiceImpl.createInstance(
                getDaoRegistryInstance().getSingleton(PHONE_DAO_SINGLETON)
        ));
        serviceSingletonRegistry.registerSingleton(GROUP_SERVICE_SINGLETON, GroupServiceImpl.createInstance(
                getDaoRegistryInstance().getSingleton(GROUP_DAO_SINGLETON),
                getServiceSingletonRegistry().getSingleton(ACCOUNT_SERVICE_SINGLETON)
        ));
        serviceSingletonRegistry.registerSingleton(GROUP_AVATAR_SERVICE_SINGLETON, GroupAvatarServiceImpl.createInstance(
                getDaoRegistryInstance().getSingleton(GROUP_AVATAR_SINGLETON)
        ));
        serviceSingletonRegistry.registerSingleton(SEARCH_SERVICE_SINGLETON, SearchServiceImpl.createInstance(
                getDaoRegistryInstance().getSingleton(SEARCH_ACCOUNT_DAO_SINGLETON),
                getDaoRegistryInstance().getSingleton(SEARCH_GROUP_DAO_SINGLETON)
        ));
    }

}
