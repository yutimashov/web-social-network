package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.message;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.Repository;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;

import java.util.List;

public interface PersonalWallMessageRepository extends Repository<Long, PersonalWallMessage> {

    List<PersonalWallMessage> getAll(Long accountId);

    List<PersonalWallMessage> getNewsFeed(Long accountId);

}
