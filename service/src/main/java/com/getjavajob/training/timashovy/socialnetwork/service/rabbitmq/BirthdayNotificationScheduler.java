package com.getjavajob.training.timashovy.socialnetwork.service.rabbitmq;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.rabbitmq.dto.BirthdayNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BirthdayNotificationScheduler {

    private final EventProducer eventProducer;
    private final AccountService accountService;
    private static final Logger logger = LoggerFactory.getLogger(BirthdayNotificationScheduler.class);

    public BirthdayNotificationScheduler(EventProducer eventProducer, AccountService accountService) {
        this.eventProducer = eventProducer;
        this.accountService = accountService;
    }

    @Scheduled(cron = "0 51 15 * * ?", zone = "Europe/Minsk")
    public void checkBirthdays() {
        sendNotification(accountService.getById(1L).get(), accountService.getById(2L).get());
        /*LocalDate today = LocalDate.now();
        List<Account> birthdayAccounts = accountService.getAccountWithBirthdayToday(today.getMonthValue(),
                today.getDayOfMonth());
        for (Account account : birthdayAccounts) {
            notifyFriends(account);
        }*/
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
        eventProducer.sendEvent(new BirthdayNotification(user.getId(), user.getFirstName(), friend.getFirstName(),
                friend.getEmail(), friend.getId()));
    }

}
