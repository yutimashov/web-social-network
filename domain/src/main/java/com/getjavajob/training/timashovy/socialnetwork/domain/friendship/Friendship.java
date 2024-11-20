package com.getjavajob.training.timashovy.socialnetwork.domain.friendship;

import com.getjavajob.training.timashovy.socialnetwork.domain.BaseEntity;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

/**
 * Model of Friendship entity in application.
 * Friendship is entity created as a connection between two Accounts: requester and receiver.
 * Requester is Account, which send friendship request to another Account - receiver.
 * Every friendship has its own status: by default the status is false. Which means that
 * requester sent friendship request to receiver, but receiver has not accepted it yet.
 * When receiver accepts request from requester, the status becomes `true`.
 *
 * @author Yuriy Timashov
 * @since 13.01.2024
 */
@Entity
@IdClass(Friendship.FriendshipId.class)
@Table(schema = "friend_data")
public class Friendship implements BaseEntity<Friendship.FriendshipId> {

    @Id
    @Column(name = "id_1")
    private Long initiatorAccountId;

    @Id
    @Column(name = "id_2")
    private Long friendAccountId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "requester_id")
    private Account requester;

    @ManyToOne
    @JoinColumn(name = "accepter_id")
    private Account receiver;

    @Column(name = "status")
    private boolean friendshipStatus;

    protected Friendship() {
    }

    public Friendship(Long initiatorAccountId, Long friendAccountId, Account requester, Account receiver,
                      boolean friendshipStatus) {
        this.initiatorAccountId = initiatorAccountId;
        this.friendAccountId = friendAccountId;
        this.requester = requester;
        this.receiver = receiver;
        this.friendshipStatus = friendshipStatus;
    }

    @Override
    public FriendshipId getId() {
        return new Friendship.FriendshipId(initiatorAccountId, friendAccountId);
    }

    @Override
    public void setId(FriendshipId friendshipId) {
        this.initiatorAccountId = friendshipId.getInitiatorAccountId();
        this.friendAccountId = friendshipId.getFriendAccountId();
    }

    public Long getInitiatorAccountId() {
        return initiatorAccountId;
    }

    public void setInitiatorAccountId(Long id1) {
        this.initiatorAccountId = id1;
    }

    public Long getFriendAccountId() {
        return friendAccountId;
    }

    public void setFriendAccountId(Long id2) {
        this.friendAccountId = id2;
    }

    public Account getRequester() {
        return requester;
    }

    public void setRequester(Account requester) {
        this.requester = requester;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public boolean getFriendshipStatus() {
        return friendshipStatus;
    }

    public void setFriendshipStatus(boolean friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return friendshipStatus == that.friendshipStatus && Objects.equals(initiatorAccountId,
                that.initiatorAccountId) && Objects.equals(friendAccountId, that.friendAccountId)
                && Objects.equals(requester, that.requester) && Objects.equals(receiver, that.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(initiatorAccountId, friendAccountId, requester, receiver, friendshipStatus);
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "firstFriendAccountId=" + initiatorAccountId +
                ", secondFriendAccountId=" + friendAccountId +
                ", requester=" + requester +
                ", receiver=" + receiver +
                ", friendshipStatus=" + friendshipStatus +
                '}';
    }

    public static class FriendshipId implements Serializable {

        private final Long initiatorAccountId;
        private final Long friendAccountId;

        public FriendshipId(Long requesterId, Long receiverId) {
            this.initiatorAccountId = requesterId;
            this.friendAccountId = receiverId;
        }

        protected Long getInitiatorAccountId() {
            return initiatorAccountId;
        }

        protected Long getFriendAccountId() {
            return friendAccountId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof FriendshipId)) return false;
            FriendshipId that = (FriendshipId) o;
            return Objects.equals(initiatorAccountId, that.initiatorAccountId) &&
                    Objects.equals(friendAccountId, that.friendAccountId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(initiatorAccountId, friendAccountId);
        }

    }

}
