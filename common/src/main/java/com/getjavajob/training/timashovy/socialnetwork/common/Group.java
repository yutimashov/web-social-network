package com.getjavajob.training.timashovy.socialnetwork.common;

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

    public Group(String groupName, String description, Long ownerId) {
        this.groupName = groupName;
        this.description = description;
        this.accountOwnerId = ownerId;
    }

    public Group(Long id, String groupName, String description, Long ownerId) {
        this(groupName, description, ownerId);
        this.id = id;
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
                group.accountOwnerId);
    }

    @Override
    public int hashCode() {
        return hash(id, groupName, description, accountOwnerId);
    }

    @Override
    public String toString() {
        return "Group {id=" + id + ", groupName=" + groupName + ", description=" + description + ", ownerId="
                + accountOwnerId + "}";
    }

}
