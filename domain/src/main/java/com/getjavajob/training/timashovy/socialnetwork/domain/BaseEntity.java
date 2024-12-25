package com.getjavajob.training.timashovy.socialnetwork.domain;

import java.io.Serializable;

/**
 * Basic interface for Entities.
 *
 * @param <T> type of id field in entity class
 */
public interface BaseEntity<T extends Serializable> {

    T getId();

    void setId(T t);

}
