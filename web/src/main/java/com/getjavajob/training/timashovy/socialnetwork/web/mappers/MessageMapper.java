package com.getjavajob.training.timashovy.socialnetwork.web.mappers;

import com.getjavajob.training.timashovy.socialnetwork.domain.message.GroupMessage;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalMessage;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.MessageDto;
import com.getjavajob.training.timashovy.socialnetwork.web.util.exceptions.WebException;

import java.io.IOException;

public class MessageMapper {

    public PersonalWallMessage toPersonalWallMessage(MessageDto messageDto, Long accountAuthorId, Long destinationId) {
        try {
            return new PersonalWallMessage.Builder()
                    .accountAuthorId(accountAuthorId)
                    .accountReceiverId(destinationId)
                    .text(messageDto.getText())
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

    public GroupMessage toGroupMessage(MessageDto messageDto, Long accountAuthorId, Long destinationId) {
        try {
            return new GroupMessage.Builder()
                    .accountAuthorId(accountAuthorId)
                    .accountAuthorId(destinationId)
                    .text(messageDto.getText())
                    .photo(messageDto.getPhoto() != null && messageDto.getPhoto().getSize() > 0
                            ? messageDto.getPhoto().getBytes() : null)
                    .build();
        } catch (IOException e) {
            throw new WebException(e.getMessage(), e.getCause());
        }
    }

}
