package com.getjavajob.dataprocessingservice.account.service.exception;

/**
 * Exception for detecting problems related to service layer.
 * Inherited from RuntimeException, because any problem with service layer can happen in runtime.
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
