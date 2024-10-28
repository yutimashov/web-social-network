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
public class Friendship {

    @Id
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private Account requester;

    @Id
    @ManyToOne
    @JoinColumn(name = "accepter_id")
    private Account receiver;

    private boolean friendshipStatus;

    public Friendship() {
    }

    public Friendship(Account requester, Account receiver, boolean friendshipStatus) {
        this.requester = requester;
        this.receiver = receiver;
        this.friendshipStatus = friendshipStatus;
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
        if (!(o instanceof Friendship)) return false;
        Friendship that = (Friendship) o;
        return Objects.equals(requester, that.requester) &&
                Objects.equals(receiver, that.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requester, receiver);
    }

    @Override
    public String toString() {
        return "Friendship{ requester=" + requester + ", receiver=" + receiver + ", friendshipStatus="
                + friendshipStatus + "}";
    }

    public static class FriendshipId implements Serializable {

        private Long requesterId;
        private Long receiverId;

        public FriendshipId() {
        }

        public FriendshipId(Long requesterId, Long receiverId) {
            this.requesterId = requesterId;
            this.receiverId = receiverId;
        }

        public Long getRequesterId() {
            return requesterId;
        }

        public void setRequesterId(Long requesterId) {
            this.requesterId = requesterId;
        }

        public Long getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(Long receiverId) {
            this.receiverId = receiverId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof FriendshipId)) return false;
            FriendshipId that = (FriendshipId) o;
            return Objects.equals(requesterId, that.requesterId) &&
                    Objects.equals(receiverId, that.receiverId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(requesterId, receiverId);
        }
    }

}
