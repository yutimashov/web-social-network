package com.getjavajob.training.timashovy.socialnetwork.domain.message;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Table(name = "personal_messages")
@Entity
public class PersonalMessage extends Message {

    @Column(name = "destination_id")
    private Long destinationId;

    protected PersonalMessage() {
    }

    private PersonalMessage(Builder builder) {
        super(builder.id, builder.accountAuthorId, builder.text, builder.photo, builder.creationDate);
        this.destinationId = builder.destinationId;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public static class Builder {
        private Long id;
        private Long destinationId;
        private Long accountAuthorId;
        private String text;
        private byte[] photo;
        private LocalDate creationDate;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder destinationId(Long destinationId) {
            this.destinationId = destinationId;
            return this;
        }

        public Builder accountAuthorId(Long accountAuthorId) {
            this.accountAuthorId = accountAuthorId;
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

        public PersonalMessage build() {
            return new PersonalMessage(this);
        }
    }

}
