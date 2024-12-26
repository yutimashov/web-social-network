package com.getjavajob.training.timashovy.socialnetwork.domain.message;

import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Table(name = "group_messages", schema = "message_data")
@Entity
public class GroupMessage extends Message {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    protected GroupMessage() {
    }

    private GroupMessage(Builder builder) {
        super(builder.id, builder.accountAuthorId, builder.text, builder.photo, builder.creationDate);
        this.group = builder.group;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public static class Builder {
        private Long id;
        private Long accountAuthorId;
        private Group group;
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

        public Builder groupId(Group group) {
            this.group = group;
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
