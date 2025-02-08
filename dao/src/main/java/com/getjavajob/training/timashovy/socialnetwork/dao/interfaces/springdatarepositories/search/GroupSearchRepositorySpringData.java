package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories.search;

import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupSearchRepositorySpringData extends CrudRepository<Group, Long> {

    @Query(value = """
            SELECT *
            FROM group_data.groups g
            WHERE g.name ILIKE '%' || :searchQuery || '%'
            	AND g.name > :lastGroupName
            ORDER BY g.name
            LIMIT :limit
            """, nativeQuery = true)
    List<Group> findGroupsSearchResult(
            @Param("accountId") String searchQuery,
            @Param("lastGroupName") String lastGroupName,
            @Param("limit") int limit);

}
