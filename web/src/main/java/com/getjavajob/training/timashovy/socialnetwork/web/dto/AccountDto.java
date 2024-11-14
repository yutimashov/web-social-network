package com.getjavajob.training.timashovy.socialnetwork.web.dto;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole;
import org.springframework.web.multipart.MultipartFile;

import static com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole.REGULAR;

public class AccountDto {

    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String icq;
    private String skype;
    private MultipartFile avatar;
    private String birthDate;
    private AccountRole role = REGULAR;

    public AccountDto() {
    }

    public AccountRole getRole() {
        return role;
    }

    public void setRole(AccountRole role) {
        this.role = role;
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

    public MultipartFile getAvatar() {
        return avatar;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

}
