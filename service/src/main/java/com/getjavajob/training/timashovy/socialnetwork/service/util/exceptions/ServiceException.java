package com.getjavajob.training.timashovy.socialnetwork.service.util.exceptions;

/**
 * Exception for detecting problems related to service layer.
 * Inherited from RuntimeException, because any problem with service layer can happen in runtime.
 *
 * @author Yuriy Timashov
 * @since 30.05.2024
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

}
