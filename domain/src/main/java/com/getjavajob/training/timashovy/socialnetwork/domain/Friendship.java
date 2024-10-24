package com.getjavajob.training.timashovy.socialnetwork.domain;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import java.util.Objects;

import static java.util.Objects.hash;

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
public class Friendship {

    private Account requester;
    private Account receiver;
    private boolean friendshipStatus;

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
        if (this == o) {
            return true;
        }
        if (!(o instanceof Friendship)) {
            return false;
        }
        Friendship friendship = (Friendship) o;
        return Objects.equals(requester, friendship.requester) && Objects.equals(receiver, friendship.receiver)
                && Objects.equals(friendshipStatus, friendship.friendshipStatus);
    }

    @Override
    public int hashCode() {
        return hash(requester, receiver, friendshipStatus);
    }

    @Override
    public String toString() {
        return "Friendship{ requester=" + requester + ", receiver=" + receiver + ", friendshipStatus="
                + friendshipStatus + "}";
    }

}
