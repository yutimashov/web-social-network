package com.getjavajob.training.timashovy.socialnetwork.common.message;

import java.io.InputStream;

public class MessageImage {

    private Long id;
    private InputStream photo;

    public MessageImage(Long id, InputStream photo) {
        this.id = id;
        this.photo = photo;
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

}
