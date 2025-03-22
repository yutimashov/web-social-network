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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
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
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    public MessageServiceImpl(GroupMessageRepository groupMessageDao, PersonalWallMessageRepository accountWallMessageDao,
                              PersonalMessageRepository personalMessageDao, GroupRepository groupRepository,
                              RedisTemplate<String, String> redisTemplate, AccountService accountService) {
        this.groupMessageDao = groupMessageDao;
        this.accountWallMessageDao = accountWallMessageDao;
        this.personalMessageDao = personalMessageDao;
        this.groupRepository = groupRepository;
        this.redisTemplate = redisTemplate;
        this.accountService = accountService;
    }

    /**
     * Get account's news feed
     * At first tries to get data from Redis, after that pulling news feed posts from DB
     */
    @Override
    public List<PersonalWallMessage> getNewsFeed(Long accountId, int pageSize) {
        logger.info("Trying get news feed for account: {}", accountId);
        String key = "feed:" + accountId;
        if (!redisTemplate.hasKey(key)) {
            logger.info("Cache has not been initialized already for account: {}", accountId);
            initializeUserFeed(accountId);
        }
        logger.info("Cache has been initialized already for account: {}", accountId);
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> tuples = zSetOps.reverseRangeWithScores(key, 0, 99);
        List<Long> postIds = new ArrayList<>();
        logger.info("Cache redis: getting data: postIds is empty");
        for (ZSetOperations.TypedTuple<String> tuple : tuples) {
            postIds.add(Long.parseLong(tuple.getValue()));
        }
        logger.info("Cache redis: getting data: postIds is full: {}", postIds);
        List<PersonalWallMessage> messagesFromRedis = accountWallMessageDao.getPostsByAccountIds(postIds);
        logger.info("Got messages from redis: {}", messagesFromRedis);
        if (messagesFromRedis.size() < pageSize) {
            logger.info("Start getting news feed from db");
            int redisNewsFeedSize = messagesFromRedis.size();
            List<PersonalWallMessage> messagesFromDb = accountWallMessageDao.findFriendMessagesByUserId(
                    accountId, redisNewsFeedSize, pageSize - redisNewsFeedSize
            );
            logger.info("Got data from db: {}", messagesFromDb);
            messagesFromRedis.addAll(messagesFromDb);
        }
        logger.info("messagesFromRedis size: {}", messagesFromRedis.size());
        return messagesFromRedis;
    }

    /**
     * Lazy initialization of feed:{userId}
     * getting 10*n (100 in this case) last posts from news feed
     */
    private void initializeUserFeed(Long userId) {
        logger.info("initialize redis cache for account: {}", userId);
        String key = "feed:" + userId;
        List<PersonalWallMessage> friendMessages = accountWallMessageDao.findNewsFeedLatestMessages(userId, 100);
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        for (PersonalWallMessage friendMessage : friendMessages) {
            zSetOps.add(key, friendMessage.getId().toString(), friendMessage.getCreationDate().atStartOfDay(ZoneOffset.UTC)
                    .toInstant().toEpochMilli());
        }
    }

    /**
     * Add new post on the wall to all friends' news feed
     */
    public void addPostToFriendsFeed(PersonalWallMessage message) {
        List<Long> friendIds = accountService.getFriendsIds(message.getAccountReceiverId());
        logger.info("account with id={} has friends: id={}", message.getAccountReceiverId(), friendIds);
        for (Long friendId : friendIds) {
            String key = "feed:" + friendId;
            ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
            zSetOps.add(key, message.getId().toString(), message.getCreationDate().atStartOfDay(ZoneOffset.UTC)
                    .toInstant().toEpochMilli());
            logger.info("add to redis cache new post: id={} from wall of account: id={} to friend: id={}",
                    message.getId(), message.getAccountReceiverId(), friendId);
            if (zSetOps.size(key) > 100) {
                zSetOps.removeRange(key, 99, 100);
            }
        }
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
        addPostToFriendsFeed(personalWallMessage);
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
    public List<Account> getAllAccountsWithPersonalMessages(Long accountId) {
        return personalMessageDao.getAllAccounts(accountId);
    }

    @Override
    public List<PersonalMessage> getAllPersonalMessagesWithAccount(Long authorId, Long receiverId) {
        return personalMessageDao.getAllPersonalMessagesWithAccount(authorId, receiverId);
    }

}
