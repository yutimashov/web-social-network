package com.getjavajob.training.timashovy.socialnetwork.common.message;

import java.io.InputStream;
import java.time.LocalDate;

public class Message {

    private Long id;
    private Long accountAuthorId;
    private MessageType messageType;
    private Long destinationId;
    private String text;
    private InputStream photo;
    private LocalDate creationDate;

    //TODO: implement Builder

}
