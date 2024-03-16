package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces;

import java.io.InputStream;

public interface ImageDao<T> {

    boolean upload(Long id, InputStream imageInputStream);

    boolean delete(Long id);

    boolean update(Long id, InputStream imageInputStream);

    InputStream get(Long id);

}
