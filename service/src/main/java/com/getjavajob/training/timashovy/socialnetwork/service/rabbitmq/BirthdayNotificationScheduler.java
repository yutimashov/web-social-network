package com.getjavajob.training.timashovy.socialnetwork.service.rabbitmq;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
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

    @Scheduled(cron = "0 31 14 * * ?", zone = "Europe/Minsk")
    public void checkBirthdays() {
        LocalDate today = LocalDate.now();
        logger.info("Start getting accounts with today`s birthday");
        Instant start = Instant.now();
        List<Account> birthdayAccounts = accountService.getAccountWithBirthdayToday(today.getMonthValue(),
                today.getDayOfMonth());
        for (Account account : birthdayAccounts) {
            notifyFriends(account);
        }
        Duration timeElapsed = Duration.between(start, Instant.now());
        logger.info("Finish job of sending event of users birthdays: " + timeElapsed.toMillis() + " ms");
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
                + friend.getLastName() + " celebrate birthday!";
        eventProducer.sendEvent(message);
    }

}
