package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.message;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.Repository;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;

import java.util.List;

public interface PersonalWallMessageRepository extends Repository<Long, PersonalWallMessage> {

    List<PersonalWallMessage> getAll(Long accountId);

    List<PersonalWallMessage> getNewsFeed(Long accountId);

    List<PersonalWallMessage> getPostsByAccountIds(List<Long> postIds);

    List<PersonalWallMessage> findFriendMessagesByUserId(Long userId, Long lastPostId, int limit);

    List<PersonalWallMessage> findNewsFeedLatestMessages(Long userId, int limit);

}
