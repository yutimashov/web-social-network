package com.getjavajob.training.timashovy.socialnetwork.web.dto;

import org.springframework.web.multipart.MultipartFile;

public class GroupDto {

    private String groupName;
    private String description;
    private MultipartFile avatar;

    public GroupDto() {}

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

}
