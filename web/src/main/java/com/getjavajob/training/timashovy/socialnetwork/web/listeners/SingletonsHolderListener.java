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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.singletonsregistry.DaoSingletonNames.*;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry.ServiceSingletonsNames.*;

public class SingletonsHolderListener implements ServletContextListener {

//    public static final String TRANSACTION_MANAGER_ATTR = "transactionManager";
//    public static final String DAO_SINGLETON_REGISTRY_ATTR = "daoSingletonRegistry";
    //public static final String SERVICE_SINGLETON_REGISTRY_ATTR = "serviceSingletonRegistry";
    public static final String APPLICATION_CONTEXT = "applicationContext";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-dao.xml",
                "beans-service.xml");
        servletContext.setAttribute(APPLICATION_CONTEXT, ctx);
//        TransactionManager transactionManager = new TransactionManager();
//        servletContext.setAttribute(TRANSACTION_MANAGER_ATTR, transactionManager);
//        DaoSingletonRegistry daoSingletonRegistry = new DaoSingletonRegistry();
//        servletContext.setAttribute(DAO_SINGLETON_REGISTRY_ATTR, daoSingletonRegistry);
//        registerDaoSingletons(transactionManager, daoSingletonRegistry);
//        ServiceSingletonRegistry serviceSingletonRegistry = new ServiceSingletonRegistry();
//        servletContext.setAttribute(SERVICE_SINGLETON_REGISTRY_ATTR, serviceSingletonRegistry);
//        registerServiceSingletons(transactionManager, daoSingletonRegistry, serviceSingletonRegistry);
    }

//    private void registerDaoSingletons(TransactionManager transactionManager,
//                                       DaoSingletonRegistry daoSingletonRegistry) {
//        daoSingletonRegistry.addSingleton(PHONE_DAO_SINGLETON, new PhoneDaoImpl(transactionManager));
//        daoSingletonRegistry.addSingleton(ACCOUNT_DAO_SINGLETON, new AccountDaoImpl(daoSingletonRegistry
//                .getSingleton(PHONE_DAO_SINGLETON), transactionManager));
//        daoSingletonRegistry.addSingleton(FRIENDSHIP_DAO_SINGLETON, new FriendshipDaoImpl());
//        daoSingletonRegistry.addSingleton(FRIENDSHIP_DAO_CHECKER_SINGLETON, new FriendshipCheckerDaoImpl());
//        daoSingletonRegistry.addSingleton(PASSWORD_DAO_SINGLETON, new PasswordDaoImpl(transactionManager));
//        daoSingletonRegistry.addSingleton(GROUP_DAO_SINGLETON, new GroupDaoImpl());
//        daoSingletonRegistry.addSingleton(GROUP_MEMBERSHIP_DAO_SINGLETON, new GroupMembershipDaoImpl());
//        daoSingletonRegistry.addSingleton(GROUP_MESSAGE_DAO_SINGLETON, new GroupMessageDaoImpl());
//        daoSingletonRegistry.addSingleton(PERSONAL_MESSAGE_DAO_SINGLETON, new PersonalMessageDaoImpl());
//        daoSingletonRegistry.addSingleton(PERSONAL_WALL_MESSAGE_DAO_SINGLETON, new PersonalWallMessageDaoImpl());
//        daoSingletonRegistry.addSingleton(SEARCH_ACCOUNT_DAO_SINGLETON, new SearchAccountDaoImpl());
//        daoSingletonRegistry.addSingleton(SEARCH_GROUP_DAO_SINGLETON, new SearchGroupDaoImpl());
//    }

//    private void registerServiceSingletons(TransactionManager transactionManager,
//                                           DaoSingletonRegistry daoSingletonRegistry,
//                                           ServiceSingletonRegistry serviceSingletonRegistry) {
//        serviceSingletonRegistry.addSingleton(PASSWORD_SERVICE_SINGLETON, new PasswordServiceImpl(
//                daoSingletonRegistry.getSingleton(PASSWORD_DAO_SINGLETON)
//        ));
//        serviceSingletonRegistry.addSingleton(PHONE_SERVICE_SINGLETON, new PhoneServiceImpl(
//                daoSingletonRegistry.getSingleton(PHONE_DAO_SINGLETON)
//        ));
//        serviceSingletonRegistry.addSingleton(ACCOUNT_SERVICE_SINGLETON, new AccountServiceImpl(
//                daoSingletonRegistry.getSingleton(ACCOUNT_DAO_SINGLETON),
//                daoSingletonRegistry.getSingleton(FRIENDSHIP_DAO_SINGLETON),
//                daoSingletonRegistry.getSingleton(FRIENDSHIP_DAO_CHECKER_SINGLETON),
//                serviceSingletonRegistry.getSingleton(PHONE_SERVICE_SINGLETON),
//                daoSingletonRegistry.getSingleton(PHONE_DAO_SINGLETON),
//                serviceSingletonRegistry.getSingleton(PASSWORD_SERVICE_SINGLETON),
//                daoSingletonRegistry.getSingleton(PASSWORD_DAO_SINGLETON), transactionManager
//        ));
//        serviceSingletonRegistry.addSingleton(LOGIN_SERVICE_SINGLETON, new LoginServiceImpl(
//                serviceSingletonRegistry.getSingleton(ACCOUNT_SERVICE_SINGLETON),
//                serviceSingletonRegistry.getSingleton(PASSWORD_SERVICE_SINGLETON)
//        ));
//        serviceSingletonRegistry.addSingleton(ADMIN_SERVICE_SINGLETON, new AdminServiceImpl(
//                serviceSingletonRegistry.getSingleton(ACCOUNT_SERVICE_SINGLETON)
//        ));
//        serviceSingletonRegistry.addSingleton(MESSAGE_SERVICE_SINGLETON, new MessageServiceImpl(
//                daoSingletonRegistry.getSingleton(GROUP_MESSAGE_DAO_SINGLETON),
//                daoSingletonRegistry.getSingleton(PERSONAL_WALL_MESSAGE_DAO_SINGLETON),
//                daoSingletonRegistry.getSingleton(PERSONAL_MESSAGE_DAO_SINGLETON),
//                serviceSingletonRegistry.getSingleton(ACCOUNT_SERVICE_SINGLETON)
//        ));
//        serviceSingletonRegistry.addSingleton(GROUP_SERVICE_SINGLETON, new GroupServiceImpl(
//                daoSingletonRegistry.getSingleton(GROUP_DAO_SINGLETON)
//        ));
//        serviceSingletonRegistry.addSingleton(GROUP_MEMBERSHIP_SERVICE_SINGLETON, new GroupMembershipServiceImpl(
//                serviceSingletonRegistry.getSingleton(ACCOUNT_SERVICE_SINGLETON),
//                daoSingletonRegistry.getSingleton(GROUP_MEMBERSHIP_DAO_SINGLETON)
//        ));
//        serviceSingletonRegistry.addSingleton(SEARCH_SERVICE_SINGLETON, new SearchServiceImpl(
//                daoSingletonRegistry.getSingleton(SEARCH_ACCOUNT_DAO_SINGLETON),
//                daoSingletonRegistry.getSingleton(SEARCH_GROUP_DAO_SINGLETON)
//        ));
//    }

}
