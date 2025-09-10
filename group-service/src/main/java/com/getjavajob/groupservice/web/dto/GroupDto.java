package com.getjavajob.groupservice.web.dto;

import org.springframework.web.multipart.MultipartFile;

public class GroupDto {

    private String name;
    private String description;
    private MultipartFile avatar;

    public GroupDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "GroupDto{" +
                "groupName='" + name + '\'' +
                ", description='" + description + '\'' +
                ", avatar=" + avatar +
                '}';
    }

}
