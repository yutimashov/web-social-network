package com.getjavajob.groupservice.web.dto;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;

import java.io.IOException;

import static java.util.Objects.isNull;

public class GroupMapper {

    public Group toGroup(GroupDto groupDto, Account account) {
        try {
            return new Group.Builder()
                    .groupName(groupDto.getName())
                    .description(groupDto.getDescription())
                    .accountOwnerId(account)
                    .avatar(!isNull(groupDto.getAvatar()) && groupDto.getAvatar().getSize() > 0
                            ? groupDto.getAvatar().getBytes() : null)
                    .build();
        } catch (IOException e) {
            throw new WebException(e.getMessage(), e.getCause());
        }
    }

}
