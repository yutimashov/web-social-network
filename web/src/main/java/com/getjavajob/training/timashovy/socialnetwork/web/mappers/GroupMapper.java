package com.getjavajob.training.timashovy.socialnetwork.web.mappers;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.GroupDto;

import java.io.IOException;

public class GroupMapper {

    public Group toGroup(GroupDto groupDto, Account account) throws IOException {
        return new Group.Builder()
                .groupName(groupDto.getGroupName())
                .description(groupDto.getDescription())
                .accountOwnerId(account)
//                .avatar(groupDto.getAvatar() != null && groupDto.getAvatar().getSize() > 0
//                        ? groupDto.getAvatar().getInputStream() : null)
                .build();
    }

}
