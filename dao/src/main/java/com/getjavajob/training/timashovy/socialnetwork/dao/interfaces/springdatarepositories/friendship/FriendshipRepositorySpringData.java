package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories.friendship;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.friendship.Friendship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FriendshipRepositorySpringData extends CrudRepository<Friendship, Long> {

    @Transactional
    @Modifying
    @Query("""
                update Friendship f
                set f.friendshipStatus = true
                where f.requester.id = :requester
                and f.receiver.id = :accepter
            """)
    int acceptRequest(@Param("requester") Long requesterId, @Param("accepter") Long accepterId);

    @Query("""
                select a
                from Account a
                join Friendship f on (f.initiatorAccountId = a.id or f.friendAccountId = a.id)
                where (f.initiatorAccountId = :accountId or f.friendAccountId = :accountId)
                and a.id != :accountId
                and f.friendshipStatus = true
            """)
    Page<Account> getFriends(@Param("accountId") Long accountId, Pageable page);

    @Query("""
                select f.requester.id
                from Friendship f
                where f.friendshipStatus = false
                and f.receiver.id = :accountId
            """)
    List<Long> getIncomingRequests(@Param("accountId") Long accountId);

    @Query("""
                select f.receiver.id
                from Friendship f
                where f.friendshipStatus = false and f.requester.id = :accountId
            """)
    List<Long> getOutgoingRequests(@Param("accountId") Long accountId);

    @Transactional
    @Modifying
    @Query("""
                delete from Friendship f
                where (f.initiatorAccountId = :accountId
                and f.friendAccountId = :deletingFriendId)
                or (f.initiatorAccountId = :deletingFriendId and f.friendAccountId = :accountId)
            """)
    void deleteFriend(@Param("accountId") Long accountId, @Param("deletingFriendId") Long deletingFriendId);

}
