package com.getjavajob.training.timashovy.socialnetwork.domain.message;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Table(name = "group_messages")
@Entity
public class GroupMessage extends Message {

    @Column(name = "group_id")
    private Long groupId;

    protected GroupMessage() {
    }

    private GroupMessage(Builder builder) {
        super(builder.id, builder.accountAuthorId, builder.text, builder.photo, builder.creationDate);
        this.groupId = builder.groupId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setDestinationId(Long groupId) {
        this.groupId = groupId;
    }

    public static class Builder {
        private Long id;
        private Long accountAuthorId;
        private Long groupId;
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

        public Builder groupId(Long groupId) {
            this.groupId = groupId;
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

        public GroupMessage build() {
            return new GroupMessage(this);
        }
    }

}
