package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.message.PersonalWallMessageRepository;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories.message.PersonalWallMessageRepositorySpringData;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonalWallMessageRepositoryImpl implements PersonalWallMessageRepository {

    private final PersonalWallMessageRepositorySpringData personalWallMessageRepositorySpringData;

    private final Logger logger = LoggerFactory.getLogger(PersonalWallMessageRepository.class);

    public PersonalWallMessageRepositoryImpl(PersonalWallMessageRepositorySpringData personalWallMessageRepositorySpringData) {
        this.personalWallMessageRepositorySpringData = personalWallMessageRepositorySpringData;
    }

    @Override
    public PersonalWallMessage save(PersonalWallMessage personalMessage) {
        personalWallMessageRepositorySpringData.save(personalMessage);
        return personalMessage;
    }

    @Override
    public Optional<PersonalWallMessage> getById(Long id) {
        return personalWallMessageRepositorySpringData.findById(id);
    }

    @Override
    public List<PersonalWallMessage> getAll(Long accountId) {
        return personalWallMessageRepositorySpringData.findAllByAccountReceiverIdOrderByCreationDateDesc(accountId);
    }

    @Override
    public List<PersonalWallMessage> getNewsFeed(Long accountId) {
        return personalWallMessageRepositorySpringData.findAccountNewsFeed(accountId);
    }

    @Override
    public List<PersonalWallMessage> getPostsByAccountIds(List<Long> postIds) {
        return personalWallMessageRepositorySpringData.getById(postIds);
    }

    @Override
    public List<PersonalWallMessage> findFriendMessagesByUserId(Long userId, Long lastPostId, int limit) {
        return personalWallMessageRepositorySpringData.findFriendMessagesByUserId(userId, lastPostId, limit);
    }

    @Override
    public List<PersonalWallMessage> findNewsFeedLatestMessages(Long userId, int limit) {
        return personalWallMessageRepositorySpringData.findNewsFeedLatestMessages(userId, limit);
    }

    @Override
    public void delete(Long id) {
        if (personalWallMessageRepositorySpringData.existsById(id)) {
            personalWallMessageRepositorySpringData.deleteById(id);
        }
    }

}
