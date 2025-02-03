package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories.message;

import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonalMessageRepositorySpringData extends CrudRepository<PersonalMessage, Long> {

    @Query("""
            select
                pm
            from
                PersonalMessage pm
            where
                (
                    pm.accountAuthorId = :authorId
                    and pm.destinationId = :receiverId
                )
                or (
                    pm.accountAuthorId = :receiverId
                    and pm.destinationId = :authorId
                )
            """)
    List<PersonalMessage> findAllPersonalMessagesBetweenAccounts(@Param("authorId") Long authorId,
                                                                 @Param("receiverId") Long receiverId);

}
