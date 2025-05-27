package com.getjavajob.training.timashovy.socialnetwork.domain.phone;

import com.getjavajob.training.timashovy.socialnetwork.domain.BaseEntity;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Class describe information about phone entity.
 * There are {@link com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType two types}
 * of phones that {@link com.getjavajob.training.timashovy.socialnetwork.domain.account.Account account}
 * can have. Each account can have several phones of each type.
 */
@Table(name = "account_phones", schema = "account_data")
@Entity
public class Phone implements BaseEntity<Long>, Serializable {

    @Serial
    private static final long serialVersionUID = 2363248207338750050L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(STRING)
    @Column(name = "phone_type")
    private PhoneType phoneType;

    @Column(name = "phone_number")
    private String number;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    protected Phone() {
    }

    public Phone(PhoneType phoneType, String number) {
        this.phoneType = phoneType;
        this.number = number;
    }

    public Phone(PhoneType phoneType, String number, Account account) {
        this.phoneType = phoneType;
        this.number = number;
        this.account = account;
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

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Phone{id=" + id + ", phoneType=" + phoneType + ", number=" + number + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(id, phone.id) && Objects.equals(phoneType, phone.phoneType)
                && Objects.equals(number, phone.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneType, number, account);
    }

}
