package com.getjavajob.training.timashovy.socialnetwork.domain.group;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.GroupMessage;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.hash;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Model of Group entity in application.
 * Contains all relevant information about Group.
 * Each group belongs to a certain account and also has some meta information.
 *
 * @author Yuriy Timashov
 * @since 10.01.2024
 */
@Entity
@Table(name = "groups", schema = "group_data")
public class Group {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "group_name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "owner_id")
    private Account accountOwner;

    @OneToMany(mappedBy = "group", cascade = ALL, orphanRemoval = true)
    private List<GroupMessage> messages = new ArrayList<>();

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] avatar;

    public void addMessage(GroupMessage message) {
        messages.add(message);
        message.setGroup(this);
    }

    public void removeMessage(GroupMessage message) {
        messages.remove(message);
        message.setGroup(null);
    }

    public Group() {
    }

    private Group(Builder builder) {
        id = builder.id;
        name = builder.groupName;
        description = builder.description;
        accountOwner = builder.accountOwner;
        avatar = builder.avatar;
        messages = builder.messages;
    }

    public static final class Builder {

        private Long id;
        private String groupName;
        private String description;
        private Account accountOwner;
        private byte[] avatar;
        private List<GroupMessage> messages;

        public Builder() {
        }

        public Builder(Group group) {
            this.id = group.getId();
            this.groupName = group.getName();
            this.description = group.getDescription();
            this.accountOwner = group.getAccountOwner();
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

        public Builder accountOwnerId(Account accountOwner) {
            this.accountOwner = accountOwner;
            return this;
        }

        public Builder avatar(byte[] avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder messages(List<GroupMessage> messages) {
            this.messages = messages;
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

    public String getName() {
        return name;
    }

    public void setName(String groupName) {
        this.name = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(Account accountOwner) {
        this.accountOwner = accountOwner;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public List<GroupMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<GroupMessage> messages) {
        this.messages = messages;
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
        return Objects.equals(id, group.id) && Objects.equals(name, group.name)
                && Objects.equals(description, group.description) && Objects.equals(accountOwner, group.accountOwner)
                && Arrays.equals(avatar, group.avatar) && Objects.equals(messages, group.messages);
    }

    @Override
    public int hashCode() {
        return hash(id, name, description, accountOwner, Arrays.hashCode(avatar), messages);
    }

    @Override
    public String toString() {
        return "Group {id=" + id + ", groupName=" + name + ", description=" + description + ", accountOwner="
                + accountOwner + ", avatar=" + Arrays.toString(avatar) + ", messages= " + messages + "}";
    }

}
