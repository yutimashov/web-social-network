package com.getjavajob.training.timashovy.socialnetwork.domain.message;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Objects;

public class Message {

    private Long id;
    private Long accountAuthorId;
    private MessageType messageType;
    private Long destinationId;
    private String text;
    private InputStream photo;
    private LocalDate creationDate;

    private Message(Builder builder) {
        id = builder.id;
        accountAuthorId = builder.accountAuthorId;
        messageType = builder.messageType;
        destinationId = builder.destinationId;
        text = builder.text;
        photo = builder.photo;
        creationDate = builder.creationDate;
    }

    public static final class Builder {
        private Long id;
        private Long accountAuthorId;
        private MessageType messageType;
        private Long destinationId;
        private String text;
        private InputStream photo;
        private LocalDate creationDate;

        public Builder() {
        }

        public Builder(Message message) {
            this.id = message.id;
            this.accountAuthorId = message.accountAuthorId;
            this.messageType = message.messageType;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder accountAuthorId(Long accountAuthorId) {
            this.accountAuthorId = accountAuthorId;
            return this;
        }

        public Builder messageType(MessageType messageType) {
            this.messageType = messageType;
            return this;
        }

        public Builder destinationId(Long destinationId) {
            this.destinationId = destinationId;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder photo(InputStream photo) {
            this.photo = photo;
            return this;
        }

        public Builder creationDate(LocalDate creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Message build() {
            return new Message(this);
        }

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

    public InputStream getPhoto() {
        return photo;
    }

    public void setPhoto(InputStream photo) {
        this.photo = photo;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(accountAuthorId, message.accountAuthorId)
                && messageType == message.messageType && Objects.equals(destinationId, message.destinationId)
                && Objects.equals(text, message.text) && Objects.equals(photo, message.photo)
                && Objects.equals(creationDate, message.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountAuthorId, messageType, destinationId, text, photo, creationDate);
    }

    @Override
    public String toString() {
        return "Message{id=" + id + ", accountAuthorId=" + accountAuthorId + ", messageType=" + messageType
                + ", destinationId=" + destinationId + ", text=" + text + ", photo=" + photo + ", creationDate="
                + creationDate + "}";
    }

}
