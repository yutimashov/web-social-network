package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import java.io.InputStream;

public interface ImageService {

    boolean create(Long id, InputStream avatarInputStream);

    boolean delete(Long id);

    InputStream get(Long id);

    boolean update(Long id, InputStream imageInputStream);

}
