package com.getjavajob.training.timashovy.socialnetwork.common;

import java.time.LocalDate;
import java.util.Objects;

import static java.util.Objects.hash;

/**
 * Model of Account entity in application.
 * Contains all relevant information about Account.
 *
 * @author Yuriy Timashov
 * @since 10.01.2023
 */
public class Account {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthDate;
    private String personalPhoneNumber;
    private String workPhoneNumber;
    private String personalAddress;
    private String workAddress;
    private String email;
    private String icq;
    private String skype;
    private String additionalInfo;

    private Account(Builder builder) {
        id = builder.id;
        firstName = builder.firstName;
        lastName = builder.lastName;
        middleName = builder.middleName;
        birthDate = builder.birthDate;
        personalPhoneNumber = builder.personalPhoneNumber;
        workPhoneNumber = builder.workPhoneNumber;
        personalAddress = builder.personalAddress;
        workAddress = builder.workAddress;
        email = builder.email;
        icq = builder.icq;
        skype = builder.skype;
        additionalInfo = builder.additionalInfo;
    }

    public static final class Builder {

        private Long id;
        private String firstName;
        private String lastName;
        private String middleName;
        private LocalDate birthDate;
        private String personalPhoneNumber;
        private String workPhoneNumber;
        private String personalAddress;
        private String workAddress;
        private String email;
        private String icq;
        private String skype;
        private String additionalInfo;

        public Builder() {
        }

        public Builder(Account account) {
            this.id = account.getId();
            this.firstName = account.getFirstName();
            this.lastName = account.getLastName();
            this.middleName = account.getMiddleName();
            this.birthDate = account.getBirthDate();
            this.personalPhoneNumber = account.getPersonalPhoneNumber();
            this.workPhoneNumber = account.getWorkPhoneNumber();
            this.workAddress = account.getWorkAddress();
            this.personalAddress = account.getPersonalAddress();
            this.email = account.getEmail();
            this.icq = account.getIcq();
            this.skype = account.getSkype();
            this.additionalInfo = account.getAdditionalInfo();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder personalPhoneNumber(String personalPhoneNumber) {
            this.personalPhoneNumber = personalPhoneNumber;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder middleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder workPhoneNumber(String workPhoneNumber) {
            this.workPhoneNumber = workPhoneNumber;
            return this;
        }

        public Builder personalAddress(String personalAddress) {
            this.personalAddress = personalAddress;
            return this;
        }

        public Builder workAddress(String workAddress) {
            this.workAddress = workAddress;
            return this;
        }

        public Builder icq(String icq) {
            this.icq = icq;
            return this;
        }

        public Builder skype(String skype) {
            this.skype = skype;
            return this;
        }

        public Builder additionalInfo(String additionalInfo) {
            this.additionalInfo = additionalInfo;
            return this;
        }

        public Account build() {
            return new Account(this);
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPersonalPhoneNumber() {
        return personalPhoneNumber;
    }

    public void setPersonalPhoneNumber(String personalPhoneNumber) {
        this.personalPhoneNumber = personalPhoneNumber;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public String getPersonalAddress() {
        return personalAddress;
    }

    public void setPersonalAddress(String personalAddress) {
        this.personalAddress = personalAddress;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcq() {
        return icq;
    }

    public void setIcq(String icq) {
        this.icq = icq;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(firstName, account.firstName)
                && Objects.equals(lastName, account.lastName) && Objects.equals(middleName, account.middleName)
                && Objects.equals(birthDate, account.birthDate) && Objects.equals(personalPhoneNumber,
                account.personalPhoneNumber) && Objects.equals(workPhoneNumber, account.workPhoneNumber)
                && Objects.equals(personalAddress, account.personalAddress) && Objects.equals(workAddress,
                account.workAddress) && Objects.equals(email, account.email) && Objects.equals(icq, account.icq)
                && Objects.equals(skype, account.skype) && Objects.equals(additionalInfo, account.additionalInfo);
    }

    @Override
    public int hashCode() {
        return hash(id, firstName, lastName, middleName, birthDate, personalPhoneNumber, workPhoneNumber,
                personalAddress, workAddress, email, icq, skype, additionalInfo);
    }

    @Override
    public String toString() {
        return "Account {id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", middleName="
                + middleName + ", birthDate=" + birthDate + ", personalPhoneNumber=" + personalPhoneNumber
                + ", workPhoneNumber=" + workPhoneNumber + ", personalAddress=" + personalAddress + ", workAddress="
                + workAddress + ", email=" + email + ", icq=" + icq + ", skype=" + skype + ", additionalInfo="
                + additionalInfo + " }";
    }

}
