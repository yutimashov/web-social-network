package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.group;

import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends CrudRepository<Group, Long> {

    @NonNull
    List<Group> findAll();

    @NonNull
    Optional<Group> findById(@NonNull Long id);

    @NonNull
    @SuppressWarnings("unchecked")
    Group save(@NonNull Group group);

}
