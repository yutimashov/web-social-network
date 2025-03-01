package com.getjavajob.training.timashovy.socialnetwork.messaging.scheduler;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.messaging.model.BirthdayNotification;
import com.getjavajob.training.timashovy.socialnetwork.messaging.producer.EventProducer;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.now;

@Service
public class BirthdayNotificationScheduler {

    private final EventProducer eventProducer;
    private final AccountService accountService;
    private static final Logger logger = LoggerFactory.getLogger(BirthdayNotificationScheduler.class);

    public BirthdayNotificationScheduler(EventProducer eventProducer, AccountService accountService) {
        this.eventProducer = eventProducer;
        this.accountService = accountService;
    }

    @Scheduled(cron = "0 48 18 * * ?", zone = "Europe/Minsk")
    public void checkBirthdays() {
        LocalDate today = now();
        List<Account> birthdayAccounts = accountService.getAccountWithBirthdayToday(today.getMonthValue(),
                today.getDayOfMonth());
        for (Account account : birthdayAccounts) {
            notifyFriends(account);
        }
    }

    public void notifyFriends(Account user) {
        long lastFriendId = 0;
        int pageSize = 1000;
        boolean hasMoreFriends = true;
        while (hasMoreFriends) {
            List<Account> friendsPage = accountService.getFriends(user.getId(), lastFriendId, pageSize);
            if (friendsPage.isEmpty()) {
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
        eventProducer.sendEvent("gjj-exchange", "notifications.birthday",
                new BirthdayNotification(user.getId(), user.getFirstName(), user.getLastName(),
                        friend.getFirstName(), friend.getLastName(), friend.getEmail(), friend.getId()));
    }

}
