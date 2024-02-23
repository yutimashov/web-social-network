package com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions;

/**
 * Exception for detecting problems related to dao layer.
 * Inherited from RuntimeException, because any problem with dao layer can happen only in runtime.
 *
 * @author Yuriy Timashov
 * @since 10.01.2024
 */
public class DaoException extends RuntimeException {

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

}
