package com.getjavajob.training.timashovy.socialnetwork.domain.password;

import com.getjavajob.training.timashovy.socialnetwork.domain.BaseEntity;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;

/**
 * Class store information about Account's password.
 * It also provides basic functionality to work with password.
 */
@Entity
@Table(name = "account_passwords", schema = "account_data")
public class Password implements BaseEntity<Long> {

    @Id
    private Long id;

    @OneToOne(fetch = LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Account account;

    @Column(name = "hash_password")
    private String passwordValue;

    public Password(String passwordValue) {
        this.passwordValue = passwordValue;
    }

    protected Password() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPasswordValue() {
        return passwordValue;
    }

    public void setPasswordValue(String password) {
        this.passwordValue = password;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(id, password1.id) && Objects.equals(account, password1.account)
                & Objects.equals(passwordValue, password1.passwordValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, passwordValue);
    }

    @Override
    public String toString() {
        return "Password{id=" + id + ", account=" + account + ", password=" + passwordValue + "}";
    }

}
