package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.group.GroupRepository;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories.GroupRepositorySpringData;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//TODO: all to spring data framework

@Repository
public class GroupDaoImpl implements GroupRepository {

    private final GroupRepositorySpringData groupRepositorySpringData;

    public GroupDaoImpl(GroupRepositorySpringData groupRepositorySpringData) {
        this.groupRepositorySpringData = groupRepositorySpringData;
    }

    @Override
    public Group create(Group group, Account account) {
        return groupRepositorySpringData.save(group);
    }

    @Override
    public Optional<Group> getById(Long id) {
        return groupRepositorySpringData.findById(id);
    }

    @Override
    public List<Group> getAll() {
        return (List<Group>) groupRepositorySpringData.findAll();
    }

    @Override
    public boolean updateById(Long id, Group group) {
        if (groupRepositorySpringData.existsById(id)) {
            group.setId(id);
            groupRepositorySpringData.save(group);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        if (groupRepositorySpringData.existsById(id)) {
            groupRepositorySpringData.deleteById(id);
            return true;
        }
        return false;
    }
}

