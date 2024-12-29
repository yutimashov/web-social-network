package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories.message;

import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonalWallMessageRepositorySpringData extends CrudRepository<PersonalWallMessage, Long> {

    @Query("""
            select pm from PersonalWallMessage pm where pm.accountReceiverId = :accountId order by pm.creationDate desc
            """)
    List<PersonalWallMessage> findAllByAccountReceiverIdOrderByCreationDateDesc(@Param("accountId") Long accountId);

}
