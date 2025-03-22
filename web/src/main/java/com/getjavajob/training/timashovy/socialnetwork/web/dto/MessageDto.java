package com.getjavajob.training.timashovy.socialnetwork.web.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class MessageDto {

    private Long accountAuthorId;
    private Long destinationId;
    private String text;
    private MultipartFile photo;
    private LocalDate creationDate = LocalDate.now();

    public MessageDto() {
    }

    public Long getAccountAuthorId() {
        return accountAuthorId;
    }

    public void setAccountAuthorId(Long accountAuthorId) {
        this.accountAuthorId = accountAuthorId;
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

}
