package com.getjavajob.training.timashovy.socialnetwork.web.mappers;

import com.getjavajob.training.timashovy.socialnetwork.domain.message.GroupMessage;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalMessage;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.MessageDto;

import java.io.IOException;

public class MessageMapper {

    public PersonalWallMessage toPersonalWallMessage(MessageDto messageDto, Long accountAuthorId, Long destinationId)
            throws IOException {
        return new PersonalWallMessage.Builder()
                .accountAuthorId(accountAuthorId)
                .accountReceiverId(destinationId)
                .text(messageDto.getText())
                .photo(messageDto.getPhoto() != null && messageDto.getPhoto().getSize() > 0
                        ? messageDto.getPhoto().getBytes() : null)
                .build();
    }

    public PersonalMessage toPersonalMessage(MessageDto messageDto, Long accountAuthorId, Long destinationId)
            throws IOException {
        return new PersonalMessage.Builder()
                .accountAuthorId(accountAuthorId)
                .destinationId(destinationId)
                .text(messageDto.getText())
                .photo(messageDto.getPhoto() != null && messageDto.getPhoto().getSize() > 0
                        ? messageDto.getPhoto().getBytes() : null)
                .build();
    }

    public GroupMessage toGroupMessage(MessageDto messageDto, Long accountAuthorId, Long destinationId)
            throws IOException {
        return new GroupMessage.Builder()
                .accountAuthorId(accountAuthorId)
                .accountAuthorId(destinationId)
                .text(messageDto.getText())
                .photo(messageDto.getPhoto() != null && messageDto.getPhoto().getSize() > 0
                        ? messageDto.getPhoto().getBytes() : null)
                .build();
    }

}
