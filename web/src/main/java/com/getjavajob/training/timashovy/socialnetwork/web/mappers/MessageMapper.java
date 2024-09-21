package com.getjavajob.training.timashovy.socialnetwork.web.mappers;

import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.MessageDto;

import java.io.IOException;

public class MessageMapper {

    public Message toMessage(MessageDto messageDto, Long accountAuthorId, Long destinationId) throws IOException {
        return new Message.Builder()
                .accountAuthorId(accountAuthorId)
                .destinationId(destinationId)
                .text(messageDto.getText())
                .photo(messageDto.getPhoto() != null && messageDto.getPhoto().getSize() > 0
                        ? messageDto.getPhoto().getInputStream() : null)
                .build();
    }

}
