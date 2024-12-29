package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.message.GroupMessageRepository;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories.GroupMessageRepositorySpringData;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.GroupMessage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GroupMessageRepositoryImpl implements GroupMessageRepository {

    private final GroupMessageRepositorySpringData groupMessageRepositorySpringData;

    public GroupMessageRepositoryImpl(GroupMessageRepositorySpringData groupMessageRepositorySpringData) {
        this.groupMessageRepositorySpringData = groupMessageRepositorySpringData;
    }

    @Override
    public GroupMessage save(GroupMessage message) {
        groupMessageRepositorySpringData.save(message);
        return message;
    }

    @Override
    public Optional<GroupMessage> getById(Long groupId) {
        return groupMessageRepositorySpringData.findById(groupId);
    }

    @Override
    public void delete(Long groupId) {
        if (groupMessageRepositorySpringData.existsById(groupId)) {
            groupMessageRepositorySpringData.deleteById(groupId);
        }
    }

    @Override
    public List<GroupMessage> getMessagesByGroupId(Long groupId) {
        return groupMessageRepositorySpringData.findByGroupId(groupId);
    }

}
