package com.getjavajob.training.timashovy.socialnetwork.common.message;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Objects;

import static java.util.Objects.hash;

public class Message {

    private Long id;
    private Long accountAuthorId;
    private LocalDate creationDate;
    private String text;
    private MessageType destination;
    private InputStream photo;

    public Message(Long accountAuthorId, LocalDate creationDate, String text, MessageType destination,
                   InputStream photo) {
        this.accountAuthorId = accountAuthorId;
        this.creationDate = creationDate;
        this.text = text;
        this.destination = destination;
        this.photo = photo;
    }

    public Message(Long id, Long accountAuthorId, LocalDate creationDate, String text, MessageType destination,
                   InputStream photo) {
        this(accountAuthorId, creationDate, text, destination, photo);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountAuthorId() {
        return accountAuthorId;
    }

    public void setAccountAuthorId(Long accountAuthorId) {
        this.accountAuthorId = accountAuthorId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MessageType getDestination() {
        return destination;
    }

    public void setDestination(MessageType destination) {
        this.destination = destination;
    }

    public InputStream getPhoto() {
        return photo;
    }

    public void setPhoto(InputStream photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(accountAuthorId, message.accountAuthorId)
                && Objects.equals(creationDate, message.creationDate) && Objects.equals(text, message.text)
                && Objects.equals(destination, message.destination);
    }

    @Override
    public int hashCode() {
        return hash(id, accountAuthorId, creationDate, text, destination, photo);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", accountAuthorId=" + accountAuthorId +
                ", creationDate=" + creationDate +
                ", text='" + text + '\'' +
                ", destination=" + destination +
                ", photo=" + photo +
                '}';
    }

}
