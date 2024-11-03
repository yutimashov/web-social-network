package com.getjavajob.training.timashovy.socialnetwork.web.mappers;

import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.MessageDto;

import java.io.IOException;

public class MessageMapper {

    public PersonalWallMessage toMessage(MessageDto messageDto, Long accountAuthorId, Long destinationId) throws IOException {
        return new PersonalWallMessage.Builder()
                .accountAuthorId(accountAuthorId)
                .accountReceiverId(destinationId)
                .text(messageDto.getText())
                .photo(messageDto.getPhoto() != null && messageDto.getPhoto().getSize() > 0
                        ? messageDto.getPhoto().getBytes() : null)
                .build();
    }

}
