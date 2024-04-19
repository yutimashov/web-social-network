package com.getjavajob.training.timashovy.socialnetwork.common.message;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

public class MessageImage {

    private Long id;
    private InputStream photo;
    private Long messageId;

    public MessageImage(InputStream photo, Long messageId) {
        this.photo = photo;
        this.messageId = messageId;
    }

    public MessageImage(Long id, InputStream photo, Long messageId) {
        this(photo, messageId);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InputStream getPhoto() {
        return photo;
    }

    public void setPhoto(InputStream photo) {
        this.photo = photo;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageImage that = (MessageImage) o;
        return Objects.equals(id, that.id) && Objects.equals(messageId, that.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, photo, messageId);
    }

    @Override
    public String toString() {
        return "MessageImage{" +
                "id=" + id +
                ", photo=" + photo +
                ", messageId=" + messageId +
                '}';
    }

}
