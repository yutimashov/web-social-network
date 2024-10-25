package com.getjavajob.training.timashovy.socialnetwork.domain.message;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Inheritance(strategy = TABLE_PER_CLASS)
public abstract class Message {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "account_author_id")
    private Long accountAuthorId;

    @Column(name = "message_text")
    private String text;

    @Lob
    @Column(name = "message_image")
    private byte[] photo;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    protected Message() {
    }

    protected Message(Long id, Long accountAuthorId, String text, byte[] photo, LocalDate creationDate) {
        this.id = id;
        this.accountAuthorId = accountAuthorId;
        this.text = text;
        this.photo = photo;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountAuthorId() {
        return accountAuthorId;
    }

    public void setAccountAuthorId(Long accountAuthorId) {
        this.accountAuthorId = accountAuthorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(accountAuthorId, message.accountAuthorId)
                && Objects.equals(text, message.text) && Arrays.equals(photo, message.photo)
                && Objects.equals(creationDate, message.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountAuthorId, text, Arrays.hashCode(photo), creationDate);
    }

    @Override
    public String toString() {
        return "Message{id=" + id + ", accountAuthorId=" + accountAuthorId + ", text=" + text + ", photo="
                + Arrays.toString(photo) + ", creationDate=" + creationDate + "}";
    }

}
