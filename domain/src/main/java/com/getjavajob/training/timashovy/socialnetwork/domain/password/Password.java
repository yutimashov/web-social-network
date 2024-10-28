package com.getjavajob.training.timashovy.socialnetwork.domain.password;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

/**
 * Class store information about Account's password.
 * It also provides basic functionality to work with password.
 */
@Entity
@Table(name = "account_passwords")
public class Password {

    @Id
    private Long id;

    @OneToOne(fetch = LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Account account;

    @Column(name = "hash_password")
    private String password;

    private String salt;

    public Password(Account account, String password, String salt) {
        this.account = account;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                & Objects.equals(password, password1.password) && Objects.equals(salt, password1.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, password, salt);
    }

    @Override
    public String toString() {
        return "Password{id=" + id + ", account=" + account + ", password=" + password + ", salt=" + salt + "}";
    }

}
