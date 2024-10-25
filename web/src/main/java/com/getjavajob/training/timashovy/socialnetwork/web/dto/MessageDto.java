package com.getjavajob.training.timashovy.socialnetwork.web.dto;

import org.springframework.web.multipart.MultipartFile;

public class MessageDto {

    private Long accountAuthorId;
    private MessageType messageType;
    private Long destinationId;
    private String text;
    private MultipartFile photo;

    public MessageDto() {
    }

    public Long getAccountAuthorId() {
        return accountAuthorId;
    }

    public void setAccountAuthorId(Long accountAuthorId) {
        this.accountAuthorId = accountAuthorId;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

}
