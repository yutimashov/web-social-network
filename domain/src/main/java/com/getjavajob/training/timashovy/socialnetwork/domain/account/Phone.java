package com.getjavajob.training.timashovy.socialnetwork.domain.account;

import java.util.Objects;

public class Phone {

    private Long id;
    private PhoneType phoneType;
    private String number;
    private Long accountId;

    public Phone(PhoneType phoneType, String number, Long accountId) {
        this.phoneType = phoneType;
        this.number = number;
        this.accountId = accountId;
    }

    public Phone(Long id, PhoneType type, String number, Long accountId) {
        this(type, number, accountId);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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
        return "Phone{id=" + id + ", phoneType=" + phoneType + ", number=" + number + ", accountId=" + accountId + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(id, phone.id) && Objects.equals(phoneType, phone.phoneType)
                && Objects.equals(number, phone.number) && Objects.equals(accountId, phone.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneType, number, accountId);
    }

}
