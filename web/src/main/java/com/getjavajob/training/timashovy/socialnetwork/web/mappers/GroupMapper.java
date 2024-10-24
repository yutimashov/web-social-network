package com.getjavajob.training.timashovy.socialnetwork.web.mappers;

import com.getjavajob.training.timashovy.socialnetwork.domain.Group;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.GroupDto;

import java.io.IOException;

public class GroupMapper {

    public Group toGroup(GroupDto groupDto, Long accountId) throws IOException {
        return new Group.Builder()
                .groupName(groupDto.getGroupName())
                .description(groupDto.getDescription())
                .accountOwnerId(accountId)
                .avatar(groupDto.getAvatar() != null && groupDto.getAvatar().getSize() > 0
                        ? groupDto.getAvatar().getInputStream() : null)
                .build();
    }

}
