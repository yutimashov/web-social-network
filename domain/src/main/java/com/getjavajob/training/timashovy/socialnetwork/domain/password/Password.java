package com.getjavajob.training.timashovy.socialnetwork.domain.password;

import com.getjavajob.training.timashovy.socialnetwork.domain.BaseEntity;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

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

    private String salt;

    public Password(String passwordValue, String salt) {
        this.passwordValue = passwordValue;
        this.salt = salt;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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
                & Objects.equals(passwordValue, password1.passwordValue) && Objects.equals(salt, password1.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, passwordValue, salt);
    }

    @Override
    public String toString() {
        return "Password{id=" + id + ", account=" + account + ", password=" + passwordValue + ", salt=" + salt + "}";
    }

}
