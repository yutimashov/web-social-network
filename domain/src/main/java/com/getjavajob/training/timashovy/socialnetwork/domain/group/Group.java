package com.getjavajob.training.timashovy.socialnetwork.domain.group;

import com.getjavajob.training.timashovy.socialnetwork.domain.util.InputStreamUtils;

import java.io.InputStream;
import java.util.Objects;

import static java.util.Objects.hash;

/**
 * Model of Group entity in application.
 * Contains all relevant information about Group.
 * Each group belongs to a certain account and also has some meta information.
 *
 * @author Yuriy Timashov
 * @since 10.01.2024
 */
public class Group {

    private Long id;
    private String groupName;
    private String description;
    private Long accountOwnerId;
    private InputStream avatar;

    private Group(Builder builder) {
        id = builder.id;
        groupName = builder.groupName;
        description = builder.description;
        accountOwnerId = builder.accountOwnerId;
        avatar = builder.avatar;
    }

    public static final class Builder {

        private Long id;
        private String groupName;
        private String description;
        private Long accountOwnerId;
        private InputStream avatar;

        public Builder() {
        }

        public Builder(Group group) {
            this.id = group.getId();
            this.groupName = group.getGroupName();
            this.description = group.getDescription();
            this.accountOwnerId = group.getAccountOwnerId();
            this.avatar = group.getAvatar();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder groupName(String groupName) {
            this.groupName = groupName;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder accountOwnerId(Long accountOwnerId) {
            this.accountOwnerId = accountOwnerId;
            return this;
        }

        public Builder avatar(InputStream avatar) {
            this.avatar = avatar;
            return this;
        }

        public Group build() {
            return new Group(this);
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAccountOwnerId() {
        return accountOwnerId;
    }

    public void setAccountOwnerId(Long accountOwnerId) {
        this.accountOwnerId = accountOwnerId;
    }

    public InputStream getAvatar() {
        return avatar;
    }

    public void setAvatar(InputStream avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Group)) {
            return false;
        }
        Group group = (Group) o;
        return Objects.equals(id, group.id) && Objects.equals(groupName, group.groupName)
                && Objects.equals(description, group.description) && Objects.equals(accountOwnerId,
                group.accountOwnerId) && InputStreamUtils.isEqual(avatar, group.avatar);
    }

    @Override
    public int hashCode() {
        return hash(id, groupName, description, accountOwnerId, avatar);
    }

    @Override
    public String toString() {
        return "Group {id=" + id + ", groupName=" + groupName + ", description=" + description + ", ownerId="
                + accountOwnerId + ", avatar=" + avatar + "}";
    }

}
