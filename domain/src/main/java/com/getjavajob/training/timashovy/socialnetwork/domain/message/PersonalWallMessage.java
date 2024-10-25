package com.getjavajob.training.timashovy.socialnetwork.domain.message;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Table(name = "personal_wall_messages")
@Entity
public class PersonalWallMessage extends Message {

    @Column(name = "account_receiver_id")
    private Long accountReceiverId;

    protected PersonalWallMessage() {
    }

    private PersonalWallMessage(Builder builder) {
        super(builder.id, builder.accountAuthorId, builder.text, builder.photo, builder.creationDate);
        this.accountReceiverId = builder.accountReceiverId;
    }

    public Long getAccountReceiverId() {
        return accountReceiverId;
    }

    public void setAccountReceiverId(Long accountReceiverId) {
        this.accountReceiverId = accountReceiverId;
    }

    public static class Builder {
        private Long id;
        private Long accountAuthorId;
        private Long accountReceiverId;
        private String text;
        private byte[] photo;
        private LocalDate creationDate;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder accountAuthorId(Long accountAuthorId) {
            this.accountAuthorId = accountAuthorId;
            return this;
        }

        public Builder accountReceiverId(Long accountReceiverId) {
            this.accountReceiverId = accountReceiverId;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder photo(byte[] photo) {
            this.photo = photo;
            return this;
        }

        public Builder creationDate(LocalDate creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public PersonalWallMessage build() {
            return new PersonalWallMessage(this);
        }
    }

}
