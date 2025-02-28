package com.getjavajob.training.timashovy.socialnetwork.service.rabbitmq.dto;

import java.time.LocalDateTime;

public class BirthdayNotification {

    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String friendFirstName;
    private String friendLastName;
    private String friendEmail;
    private Long friendId;
    private LocalDateTime creationDateTime;

    public BirthdayNotification(Long userId, String userFirstName, String userLastName, String friendFirstName,
                                String friendLastName, String friendEmail, Long friendId) {
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.friendFirstName = friendFirstName;
        this.friendLastName = friendLastName;
        this.friendEmail = friendEmail;
        this.friendId = friendId;
        this.creationDateTime = LocalDateTime.now();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getFriendLastName() {
        return friendLastName;
    }

    public void setFriendLastName(String friendLastName) {
        this.friendLastName = friendLastName;
    }

    public String getFriendFirstName() {
        return friendFirstName;
    }

    public void setFriendFirstName(String friendFirstName) {
        this.friendFirstName = friendFirstName;
    }

}
