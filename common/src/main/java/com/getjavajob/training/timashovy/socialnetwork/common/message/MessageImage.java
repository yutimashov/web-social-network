package com.getjavajob.training.timashovy.socialnetwork.common.message;

import java.io.InputStream;

public class MessageImage {

    private Long id;
    private InputStream photo;
    private Long messageId;

    public MessageImage(Long id, InputStream photo, Long messageId) {
        this.id = id;
        this.photo = photo;
        this.messageId = messageId;
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

}
