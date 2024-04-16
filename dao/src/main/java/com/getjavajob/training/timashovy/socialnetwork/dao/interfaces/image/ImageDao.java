package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.image;

import java.io.InputStream;

public interface ImageDao {

    boolean upload(Long id, InputStream imageInputStream);

    boolean delete(Long id);

    InputStream get(Long id);

    boolean update(Long id, InputStream imageInputStream);

}
