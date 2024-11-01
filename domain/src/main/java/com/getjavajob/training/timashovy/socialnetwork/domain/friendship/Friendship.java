package com.getjavajob.training.timashovy.socialnetwork.domain.friendship;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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
public class Friendship {

    @Id
    @Column(name = "id_1")
    private Long firstFriendAccountId;

    @Id
    @Column(name = "id_2")
    private Long secondFriendAccountId;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private Account requester;

    @ManyToOne
    @JoinColumn(name = "accepter_id")
    private Account receiver;

    @Column(name = "status")
    private boolean friendshipStatus;

    protected Friendship() {
    }

    public Friendship(Long firstFriendAccountId, Long secondFriendAccountId, Account requester, Account receiver,
                      boolean friendshipStatus) {
        this.firstFriendAccountId = firstFriendAccountId;
        this.secondFriendAccountId = secondFriendAccountId;
        this.requester = requester;
        this.receiver = receiver;
        this.friendshipStatus = friendshipStatus;
    }

    public Long getFirstFriendAccountId() {
        return firstFriendAccountId;
    }

    public void setFirstFriendAccountId(Long id1) {
        this.firstFriendAccountId = id1;
    }

    public Long getSecondFriendAccountId() {
        return secondFriendAccountId;
    }

    public void setSecondFriendAccountId(Long id2) {
        this.secondFriendAccountId = id2;
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
        return friendshipStatus == that.friendshipStatus && Objects.equals(firstFriendAccountId,
                that.firstFriendAccountId) && Objects.equals(secondFriendAccountId, that.secondFriendAccountId)
                && Objects.equals(requester, that.requester) && Objects.equals(receiver, that.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstFriendAccountId, secondFriendAccountId, requester, receiver, friendshipStatus);
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "firstFriendAccountId=" + firstFriendAccountId +
                ", secondFriendAccountId=" + secondFriendAccountId +
                ", requester=" + requester +
                ", receiver=" + receiver +
                ", friendshipStatus=" + friendshipStatus +
                '}';
    }

    public static class FriendshipId implements Serializable {

        private Long firstFriendAccountId;
        private Long secondFriendAccountId;

        public FriendshipId() {
        }

        public FriendshipId(Long requesterId, Long receiverId) {
            this.firstFriendAccountId = requesterId;
            this.secondFriendAccountId = receiverId;
        }

        public Long getFirstFriendAccountId() {
            return firstFriendAccountId;
        }

        public void setFirstFriendAccountId(Long firstFriendAccountId) {
            this.firstFriendAccountId = firstFriendAccountId;
        }

        public Long getSecondFriendAccountId() {
            return secondFriendAccountId;
        }

        public void setSecondFriendAccountId(Long secondFriendAccountId) {
            this.secondFriendAccountId = secondFriendAccountId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof FriendshipId)) return false;
            FriendshipId that = (FriendshipId) o;
            return Objects.equals(firstFriendAccountId, that.firstFriendAccountId) &&
                    Objects.equals(secondFriendAccountId, that.secondFriendAccountId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstFriendAccountId, secondFriendAccountId);
        }

    }

}
