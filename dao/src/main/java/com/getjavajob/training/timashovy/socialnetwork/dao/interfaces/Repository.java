package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.domain.BaseEntity;

import java.io.Serializable;
import java.util.Optional;

public interface Repository<K extends Serializable, T extends BaseEntity<K>> {

    T save(T entity);

    Optional<T> getById(K id);

    void delete(K id);

}
