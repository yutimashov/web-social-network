package com.getjavajob.training.timashovy.socialnetwork.common.message;

import java.time.LocalDate;
import java.util.Objects;

import static java.util.Objects.hash;

public class Message {

    private Long id;
    private Long accountAuthorId;
    private LocalDate creationDate;
    private String text;
    private MessageType destination;

    public Message(Long accountAuthorId, String text, MessageType destination) {
        this.accountAuthorId = accountAuthorId;
        this.text = text;
        this.destination = destination;
    }

    public Message(Long id, Long accountAuthorId, String text, MessageType destination) {
        this(accountAuthorId, text, destination);
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
        return hash(id, accountAuthorId, creationDate, text, destination);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", accountAuthorId=" + accountAuthorId +
                ", creationDate=" + creationDate +
                ", text='" + text + '\'' +
                ", destination=" + destination +
                '}';
    }

}
