package com.getjavajob.training.timashovy.socialnetwork.service.util.exceptions;

public class ServiceException extends RuntimeException {

    public ServiceException(Throwable e) {
        super(e);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
