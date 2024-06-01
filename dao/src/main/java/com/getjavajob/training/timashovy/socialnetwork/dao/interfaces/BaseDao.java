package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionWrapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Interface contains general dao-methods, which describes common behaviour of dao operations with entities.
 *
 * @param <T> type of entity class
 */
public interface BaseDao<T> {

    Long create(T t);

    Optional<T> getById(Long id);

    List<T> getAll();

    boolean updateById(Long id, T t);

    boolean deleteById(Long id);

}
