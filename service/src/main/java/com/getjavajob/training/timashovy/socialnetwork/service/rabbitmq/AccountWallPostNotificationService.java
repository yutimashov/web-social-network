package com.getjavajob.training.timashovy.socialnetwork.service.rabbitmq;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountWallPostNotificationService {

    private final EventProducer eventProducer;
    private final AccountService accountService;

    public AccountWallPostNotificationService(EventProducer eventProducer, AccountService accountService) {
        this.eventProducer = eventProducer;
        this.accountService = accountService;
    }

    public void notifyFriends(Account user) {
        long lastFriendId = 0;
        int pageSize = 1000;
        boolean hasMoreFriends = true;
        while (hasMoreFriends) {
            List<Account> friendsPage = accountService.getFriends(user.getId(), lastFriendId, pageSize);
            if (friendsPage.isEmpty()) {
                hasMoreFriends = false;
                break;
            }
            for (Account friend : friendsPage) {
                sendNotification(user, friend);
                lastFriendId = friend.getId();
            }
            if (friendsPage.size() < pageSize) {
                hasMoreFriends = false;
            }
        }
    }

    private void sendNotification(Account user, Account friend) {
        String message = "Hey, " + user.getFirstName() + ", your friend " + friend.getFirstName() + " "
                + friend.getLastName() + " has just publish a new post!";
        eventProducer.sendEvent(message);
    }

}
