package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories.message;

import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonalWallMessageRepositorySpringData extends CrudRepository<PersonalWallMessage, Long> {

    @Query("""
            select
                pm
            from
                PersonalWallMessage pm
            where
                pm.accountReceiverId = :accountId
            order by
                pm.creationDate desc
            """)
    List<PersonalWallMessage> findAllByAccountReceiverIdOrderByCreationDateDesc(@Param("accountId") Long accountId);

    @Query(value = """
            SELECT m.*
             FROM message_data.personal_wall_messages m
             WHERE account_receiver_id IN (
             	SELECT id_2 AS friend_id FROM friend_data.friendship WHERE id_1 = :accountId AND status = true
                 UNION ALL
                 SELECT id_1 AS friend_id FROM friend_data.friendship WHERE id_2 = :accountId AND status = true
             )
             ORDER BY m.creation_date DESC;
            """, nativeQuery = true
    )
    List<PersonalWallMessage> findAccountNewsFeed(@Param("accountId") Long accountId);

    @Query("SELECT m FROM PersonalWallMessage m WHERE m.id IN :postIds ORDER BY m.creationDate desc")
    List<PersonalWallMessage> getById(@Param("postIds") List<Long> postIds);

    @Query(value = """
            SELECT DISTINCT m.*
            FROM message_data.personal_wall_messages m
            JOIN friend_data.friendship f
            ON m.account_receiver_id = f.id_2 OR m.account_receiver_id = f.id_1
            WHERE (:userId = f.id_1 OR :userId = f.id_2 AND m.account_author_id != m.account_receiver_id)
            AND f.status = true
            AND m.account_author_id != :userId
            AND m.account_receiver_id != :userId
            ORDER BY m.creation_date DESC
            OFFSET :offset
            LIMIT :limit;
            """, nativeQuery = true
    )
    List<PersonalWallMessage> findFriendMessagesByUserId(
            @Param("userId") Long userId,
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    @Query(value = """
            SELECT DISTINCT m.*
            FROM message_data.personal_wall_messages m
            JOIN friend_data.friendship f ON m.account_receiver_id = f.id_2 OR m.account_receiver_id = f.id_1
            WHERE (:userId = f.id_1 OR :userId = f.id_2)
            AND m.account_author_id != :userId
            AND m.account_receiver_id != :userId
            AND f.status = true
            ORDER BY m.creation_date DESC
            LIMIT :limit;
            """, nativeQuery = true
    )
    List<PersonalWallMessage> findNewsFeedLatestMessages(@Param("userId") Long userId, @Param("limit") int limit);

}
