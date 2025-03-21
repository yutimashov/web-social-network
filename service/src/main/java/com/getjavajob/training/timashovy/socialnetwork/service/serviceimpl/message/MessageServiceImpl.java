package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.group.GroupRepository;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.message.GroupMessageRepository;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.message.PersonalMessageRepository;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.message.PersonalWallMessageRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.GroupMessage;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalMessage;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MessageServiceImpl implements MessageService {

    private final GroupMessageRepository groupMessageDao;
    private final PersonalWallMessageRepository accountWallMessageDao;
    private final PersonalMessageRepository personalMessageDao;
    private final GroupRepository groupRepository;
    private final AccountService accountService;
    private final RedisTemplate<String, String> redisTemplate;

    public MessageServiceImpl(GroupMessageRepository groupMessageDao, PersonalWallMessageRepository accountWallMessageDao,
                              PersonalMessageRepository personalMessageDao,
                              GroupRepository groupRepository, RedisTemplate<String, String> redisTemplate,
                              AccountService accountService) {
        this.groupMessageDao = groupMessageDao;
        this.accountWallMessageDao = accountWallMessageDao;
        this.personalMessageDao = personalMessageDao;
        this.groupRepository = groupRepository;
        this.redisTemplate = redisTemplate;
        this.accountService = accountService;
    }

    /**
     * Get account's news feed
     * At first tries to get data from Redis, after that - from DB
     */
    public List<PersonalWallMessage> getNewsFeed(Long accountId, int page, int pageSize) {
        String key = "feed:" + accountId;
        if (!redisTemplate.hasKey(key)) {
            initializeUserFeed(accountId);
        }
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> tuples = zSetOps.reverseRangeWithScores(key, (long) page * pageSize,
                (long) (page + 1) * pageSize - 1);
        List<Long> postIds = new ArrayList<>();
        for (ZSetOperations.TypedTuple<String> tuple : tuples) {
            postIds.add(Long.parseLong(tuple.getValue()));
        }
        List<PersonalWallMessage> messagesFromRedis = accountWallMessageDao.getPostsByAccountIds(postIds);
        if (messagesFromRedis.size() < pageSize) {
            List<PersonalWallMessage> messagesFromDb = accountWallMessageDao.findFriendMessagesByUserId(
                    accountId, messagesFromRedis.size(), pageSize - messagesFromRedis.size());
            messagesFromRedis.addAll(messagesFromDb);
        }
        return messagesFromRedis;
    }

    /**
     * Инициализирует feed:{userId} для пользователя.
     */
    private void initializeUserFeed(Long userId) {
        String key = "feed:" + userId;
        List<Long> friendIds = accountService.getFriends(userId);
        List<Message> friendMessages = messageRepository.findLatestMessagesByUserIds(friendIds, 100);
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        friendMessages.forEach(message -> {
            zSetOps.add(key, message.getId().toString(), message.getCreationDate().toEpochMilli());
        });
        // Ограничиваем размер ZSet до 10*n
        zSetOps.trim(key, 0, 100 - 1); // n = 10, ограничение до 100 элементов
    }

    /**
     * Добавляет новое сообщение в ленты всех друзей автора.
     */
    public void addPostToFriendsFeed(Message message) {
        // Находим всех друзей автора
        List<Long> friendIds = accountService.getFriends(message.getAuthorId());
        // Добавляем сообщение в feed каждого друга
        friendIds.forEach(friendId -> {
            String key = "feed:" + friendId;
            ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
            zSetOps.add(key, message.getId().toString(), message.getCreatedTime().toEpochMilli());
            // Ограничиваем размер ZSet до 10*n
            zSetOps.trim(key, 0, 100 - 1); // n = 10, ограничение до 100 элементов
        });
    }

    @Transactional
    @Override
    public void createGroupMessage(GroupMessage groupMessage, Long groupId) {
        groupMessage.setGroup(groupRepository.getById(groupId).get());
        groupMessageDao.save(groupMessage);
    }

    @Transactional
    @Override
    public void createPersonalWallMessage(PersonalWallMessage personalWallMessage) {
        accountWallMessageDao.save(personalWallMessage);
    }

    @Transactional
    @Override
    public void createPersonalMessage(PersonalMessage personalMessage) {
        personalMessageDao.save(personalMessage);
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public Message getGroupMessageById(Long id) {
        if (groupMessageDao.getById(id).isPresent()) {
            return groupMessageDao.getById(id).get();
        }
        return null;
    }

    @Override
    public Message getAccountWallMessageById(Long accountId) {
        if (accountWallMessageDao.getById(accountId).isPresent()) {
            return accountWallMessageDao.getById(accountId).get();
        }
        return null;
    }

    @Override
    public Message getPersonalMessageById(Long messageId) {
        if (personalMessageDao.getById(messageId).isPresent()) {
            return personalMessageDao.getById(messageId).get();
        }
        return null;
    }

    @Override
    public List<GroupMessage> getMessagesByGroupId(Long groupId) {
        return groupMessageDao.getMessagesByGroupId(groupId);
    }

    @Override
    public List<PersonalWallMessage> getAllAccountWallMessages(Long destinationId) {
        return accountWallMessageDao.getAll(destinationId);
    }

    @Override
    public List<PersonalWallMessage> getNewsFeed(Long destinationId) {
        return accountWallMessageDao.getNewsFeed(destinationId);
    }

    @Override
    public List<Account> getAllAccountsWithPersonalMessages(Long accountId) {
        return personalMessageDao.getAllAccounts(accountId);
    }

    @Override
    public List<PersonalMessage> getAllPersonalMessagesWithAccount(Long authorId, Long receiverId) {
        return personalMessageDao.getAllPersonalMessagesWithAccount(authorId, receiverId);
    }

}
