package com.getjavajob.training.timashovy.socialnetwork.domain.account;

import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole.REGULAR;
import static java.util.Objects.hash;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Model of Account entity in application.
 * Contains all relevant information about Account.
 *
 * @author Yuriy Timashov
 * @since 10.01.2024
 */
@Table(name = "accounts", schema = "account_data")
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "personal_address")
    private String personalAddress;

    @Column(name = "work_address")
    private String workAddress;

    private String email;
    private String icq;
    private String skype;

    @Column(name = "additional_info")
    private String additionalInfo;

    @Enumerated(STRING)
    @Column(name = "role_type")
    private AccountRole role = REGULAR;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] avatar;

    @OneToMany(mappedBy = "account", cascade = ALL, orphanRemoval = true)
    private List<Phone> phones = new ArrayList<>();

    public Account() {
    }

    private Account(Builder builder) {
        id = builder.id;
        firstName = builder.firstName;
        lastName = builder.lastName;
        middleName = builder.middleName;
        birthDate = builder.birthDate;
        phones = builder.phones;
        personalAddress = builder.personalAddress;
        workAddress = builder.workAddress;
        email = builder.email;
        icq = builder.icq;
        skype = builder.skype;
        additionalInfo = builder.additionalInfo;
        role = builder.role;
        avatar = builder.avatar;
    }

    public static class Builder {

        private Long id;
        private String firstName;
        private String lastName;
        private String middleName;
        private LocalDate birthDate;
        private List<Phone> phones;
        private String personalAddress;
        private String workAddress;
        private String email;
        private String icq;
        private String skype;
        private String additionalInfo;
        private AccountRole role;
        private byte[] avatar;

        public Builder() {
        }

        public Builder(Account account) {
            this.id = account.getId();
            this.firstName = account.getFirstName();
            this.lastName = account.getLastName();
            this.middleName = account.getMiddleName();
            this.birthDate = account.getBirthDate();
            this.phones = account.getPhones();
            this.workAddress = account.getWorkAddress();
            this.personalAddress = account.getPersonalAddress();
            this.email = account.getEmail();
            this.icq = account.getIcq();
            this.skype = account.getSkype();
            this.additionalInfo = account.getAdditionalInfo();
            this.role = account.getRole();
            this.avatar = account.getAvatar();
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

        public Builder phones(List<Phone> phones) {
            this.phones = phones;
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

        public Builder role(AccountRole role) {
            this.role = role;
            return this;
        }

        public Builder avatar(byte[] avatar) {
            this.avatar = avatar;
            return this;
        }

        public Account build() {
            return new Account(this);
        }

    }

    public void addPhone(Phone phone) {
        phones.add(phone);
        phone.setAccount(this);
    }

    public void removePhone(Phone phone) {
        phones.remove(phone);
        phone.setAccount(null);
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

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
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

    public AccountRole getRole() {
        return role;
    }

    public void setRole(AccountRole role) {
        this.role = role;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
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
                && Objects.equals(birthDate, account.birthDate) && Objects.equals(phones, account.phones)
                && Objects.equals(personalAddress, account.personalAddress)
                && Objects.equals(workAddress, account.workAddress) && Objects.equals(email, account.email)
                && Objects.equals(icq, account.icq) && Objects.equals(skype, account.skype)
                && Objects.equals(additionalInfo, account.additionalInfo) && Objects.equals(role, account.role)
                && Arrays.equals(avatar, account.avatar);
    }

    @Override
    public int hashCode() {
        return hash(id, firstName, lastName, middleName, birthDate, phones, personalAddress, workAddress, email, icq,
                skype, additionalInfo, role, Arrays.hashCode(avatar));
    }

    @Override
    public String toString() {
        return "Account {id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", middleName="
                + middleName + ", birthDate=" + birthDate + ", phones=" + phones + ", personalAddress="
                + personalAddress + ", workAddress=" + workAddress + ", email=" + email + ", icq=" + icq + ", skype="
                + skype + ", additionalInfo=" + additionalInfo + ", role= " + role + " }";
    }

}
