package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Interface contains general dao-methods, which describes common behaviour of dao operations with entities.
 *
 * @param <T> type of entity class
 */
public interface AccountGroupDao<T> {

    Long create(T t);

    Optional<T> getById(Long id);

    List<T> getAll();

    boolean updateById(Long id, T t);

    boolean deleteById(Long id);

}
