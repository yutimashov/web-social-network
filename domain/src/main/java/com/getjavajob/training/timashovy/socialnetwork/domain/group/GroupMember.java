package com.getjavajob.training.timashovy.socialnetwork.domain.group;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "group_members", schema = "group_data")
public class GroupMember {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "is_admin")
    private boolean admin;

    @Column(name = "is_member")
    private boolean member;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    public GroupMember() {
    }

    public GroupMember(Account account, Group group) {
        this.account = account;
        this.group = group;
    }

    public GroupMember(Account account, Group group, boolean admin, boolean member, LocalDate registrationDate) {
        this.account = account;
        this.group = group;
        this.admin = admin;
        this.member = member;
        this.registrationDate = registrationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMember that = (GroupMember) o;
        return admin == that.admin && member == that.member && Objects.equals(id, that.id)
                && Objects.equals(account, that.account) && Objects.equals(group, that.group)
                && Objects.equals(registrationDate, that.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, group, admin, member, registrationDate);
    }

    @Override
    public String toString() {
        return "GroupMember{" +
                "id=" + id +
                ", account=" + account +
                ", group=" + group +
                ", admin=" + admin +
                ", member=" + member +
                ", registrationDate=" + registrationDate +
                '}';
    }

}
