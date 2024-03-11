package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces;

import java.io.InputStream;

public interface ImageDao<T> {

    boolean upload(T entity, InputStream imageInputStream);

    boolean delete(Long imageId);

    boolean update(Long imageId);

}
