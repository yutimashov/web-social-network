package com.getjavajob.training.timashovy.socialnetwork.domain.phone;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Table(name = "account_phones")
@Entity
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_type")
    @Enumerated(STRING)
    private PhoneType phoneType;

    @Column(name = "phone_number")
    private String number;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    protected Phone() {
    }

    public Phone(PhoneType phoneType, String number, Account account) {
        this.phoneType = phoneType;
        this.number = number;
        this.account = account;
    }

    public Phone(Long id, PhoneType type, String number, Account account) {
        this(type, number, account);
        this.id = id;
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
        return "Phone{id=" + id + ", phoneType=" + phoneType + ", number=" + number + ", accountId=" + account + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(id, phone.id) && Objects.equals(phoneType, phone.phoneType)
                && Objects.equals(number, phone.number) && Objects.equals(account, phone.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneType, number, account);
    }

}
