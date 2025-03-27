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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.Long.parseLong;
import static java.time.ZoneOffset.UTC;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public class MessageServiceImpl implements MessageService {

    private final GroupMessageRepository groupMessageDao;
    private final PersonalWallMessageRepository accountWallMessageDao;
    private final PersonalMessageRepository personalMessageDao;
    private final GroupRepository groupRepository;
    private final AccountService accountService;
    private final RedisTemplate<String, String> redisTemplate;
    private static final Logger logger = getLogger(MessageServiceImpl.class);

    private final int newsFeedCacheSize = 10;

    public MessageServiceImpl(GroupMessageRepository groupMessageDao,
                              PersonalWallMessageRepository accountWallMessageDao,
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
    public List<PersonalWallMessage> getNewsFeed(Long accountId, Long lastPostId, Long cacheStartRange, int pageSize) {
        String key = "feed:" + accountId;
        if (!redisTemplate.hasKey(key)) {
            initializeUserFeed(accountId);
        }
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        long finishRangeIndex = cacheStartRange + newsFeedCacheSize - 1;
        Set<ZSetOperations.TypedTuple<String>> tuples = zSetOps.reverseRangeWithScores(key, cacheStartRange,
                finishRangeIndex);
        List<Long> postIds = new ArrayList<>();
        if (!tuples.isEmpty()) {
            for (ZSetOperations.TypedTuple<String> tuple : tuples) {
                postIds.add(parseLong(tuple.getValue()));
            }
        }
        List<PersonalWallMessage> newsFeedPosts = accountWallMessageDao.getPostsByAccountIds(postIds);
        if (newsFeedPosts.isEmpty()) {
            return accountWallMessageDao.findFriendMessagesByUserId(
                    accountId, lastPostId, pageSize
            );
        }
        return newsFeedPosts;
    }

    /**
     * Lazy initialization of feed:{userId}
     */
    private void initializeUserFeed(Long userId) {
        String key = "feed:" + userId;
        List<PersonalWallMessage> friendMessages = accountWallMessageDao.findNewsFeedLatestMessages(userId,
                newsFeedCacheSize);
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        for (PersonalWallMessage friendMessage : friendMessages) {
            zSetOps.add(key, friendMessage.getId().toString(), friendMessage.getCreationDate().atStartOfDay(UTC)
                    .toInstant().toEpochMilli());
        }
    }

    /**
     * Add new post on the wall to all friends' news feed
     */
    public void addPostToFriendsFeed(PersonalWallMessage message) {
        List<Long> friendIds = accountService.getFriendsIds(message.getAccountReceiverId());
        for (Long friendId : friendIds) {
            String key = "feed:" + friendId;
            ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
            zSetOps.add(key, message.getId().toString(), message.getCreationDate().atStartOfDay(UTC)
                    .toInstant().toEpochMilli());
            if (zSetOps.size(key) > newsFeedCacheSize) {
                zSetOps.removeRange(key, newsFeedCacheSize - 1, newsFeedCacheSize);
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
