package com.getjavajob.messageservice.web.dto;

public class WebException extends RuntimeException {

    public WebException(String message) {
        super(message);
    }

    public WebException(String message, Throwable cause) {
        super(message, cause);
    }

}
