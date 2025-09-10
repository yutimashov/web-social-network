package com.getjavajob.accountservice.dao;

import com.getjavajob.training.timashovy.socialnetwork.domain.BaseEntity;

import java.io.Serializable;
import java.util.Optional;

/**
 * General interface with basic operation available for working with entity classes.
 *
 * @param <K> type of entity identifier
 * @param <T> type of entity
 */
public interface Repository<K extends Serializable, T extends BaseEntity<K>> {

    T save(T entity);

    Optional<T> getById(K id);

    void delete(K id);

}
