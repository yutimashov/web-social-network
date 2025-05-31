package com.getjavajob.messageservice.web.dto;

import com.getjavajob.training.timashovy.socialnetwork.domain.message.GroupMessage;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalMessage;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;

import java.io.IOException;

public class MessageMapper {

    public PersonalWallMessage toPersonalWallMessage(MessageDto messageDto, Long accountAuthorId, Long destinationId) {
        try {
            return new PersonalWallMessage.Builder()
                    .accountAuthorId(accountAuthorId)
                    .accountReceiverId(destinationId)
                    .text(messageDto.getText())
                    .creationDate(messageDto.getCreationDate())
                    .photo(messageDto.getPhoto() != null && messageDto.getPhoto().getSize() > 0
                            ? messageDto.getPhoto().getBytes() : null)
                    .build();
        } catch (IOException e) {
            throw new WebException(e.getMessage(), e.getCause());
        }
    }

    public PersonalMessage toPersonalMessage(MessageDto messageDto, Long accountAuthorId, Long destinationId) {
        try {
            return new PersonalMessage.Builder()
                    .accountAuthorId(accountAuthorId)
                    .destinationId(destinationId)
                    .text(messageDto.getText())
                    .photo(messageDto.getPhoto() != null && messageDto.getPhoto().getSize() > 0
                            ? messageDto.getPhoto().getBytes() : null)
                    .build();
        } catch (IOException e) {
            throw new WebException(e.getMessage(), e.getCause());
        }
    }

    public GroupMessage toGroupMessage(MessageDto messageDto, Long accountAuthorId) {
        try {
            return new GroupMessage.Builder()
                    .accountAuthorId(accountAuthorId)
                    .text(messageDto.getText())
                    .photo(messageDto.getPhoto() != null && messageDto.getPhoto().getSize() > 0
                            ? messageDto.getPhoto().getBytes() : null)
                    .build();
        } catch (IOException e) {
            throw new WebException(e.getMessage(), e.getCause());
        }
    }

}
