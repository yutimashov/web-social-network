package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories.friendship;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.friendship.Friendship;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendshipRepositorySpringData extends CrudRepository<Friendship, Long> {

    @Query(value = """
            SELECT a.*
            FROM account_data.accounts a
            JOIN (
                SELECT id_2 AS friend_id FROM friend_data.friendship WHERE id_1 = :accountId AND status = true
                UNION ALL
                SELECT id_1 AS friend_id FROM friend_data.friendship WHERE id_2 = :accountId AND status = true
            ) f ON a.id = f.friend_id
            WHERE a.id > :lastId
            ORDER BY a.id
            LIMIT :limit
            """, nativeQuery = true)
    List<Account> findFriendsByAccountId(
            @Param("accountId") Long accountId,
            @Param("lastId") Long lastId,
            @Param("limit") int limit);

    @Query(value = """
            SELECT a.*
                        FROM account_data.accounts a
                                 JOIN (SELECT requester_id AS req_id
                                        FROM friend_data.friendship
                                        WHERE accepter_id = :accountId
                                            AND status = false) f ON a.id = f.req_id
                                            WHERE a.id > :lastId
            ORDER BY a.id
            LIMIT :limit
            """, nativeQuery = true)
    List<Account> findFollowerAccountsById(@Param("accountId") Long accountId,
                                           @Param("lastId") Long lastId,
                                           @Param("limit") int limit);

    @Query(value = """
            SELECT a.*
                        FROM account_data.accounts a
                                 JOIN (SELECT accepter_id AS acc_id
                                       FROM friend_data.friendship
                                       WHERE requester_id = :accountId
                                         AND status = false) f ON a.id = f.acc_id
                                         WHERE a.id > :lastId
            ORDER BY a.id
            LIMIT :limit
            """, nativeQuery = true)
    List<Account> findFollowingAccountsById(@Param("accountId") Long accountId,
                                            @Param("lastId") Long lastId,
                                            @Param("limit") int limit);

    @Modifying
    @Query("""
                update
                    Friendship f
                set
                    f.friendshipStatus = true
                where
                    f.requester.id = :requester
                    and f.receiver.id = :accepter
            """)
    int acceptRequest(@Param("requester") Long requesterId, @Param("accepter") Long accepterId);

    @Modifying
    @Query("""
                delete from
                    Friendship f
                where
                    (
                        f.initiatorAccountId = :accountId
                        and f.friendAccountId = :deletingFriendId
                    )
                    or (
                        f.initiatorAccountId = :deletingFriendId
                        and f.friendAccountId = :accountId
                    )
            """)
    void deleteFriend(@Param("accountId") Long accountId, @Param("deletingFriendId") Long deletingFriendId);

}
