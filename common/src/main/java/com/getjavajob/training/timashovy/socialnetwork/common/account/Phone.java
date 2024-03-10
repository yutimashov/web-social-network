package com.getjavajob.training.timashovy.socialnetwork.common.account;

public class Phone {

    private Long id;
    private PhoneType phoneType;
    private String number;
    private Long accountId;

    public Phone(PhoneType phoneType, String number) {
        this.phoneType = phoneType;
        this.number = number;
    }

    public Phone(PhoneType phoneType, String number, Long accountId) {
        this(phoneType, number);
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
        return "Phone{" +
                "id=" + id +
                ", phoneType=" + phoneType +
                ", number='" + number + '\'' +
                ", accountId=" + accountId +
                '}';
    }

}
