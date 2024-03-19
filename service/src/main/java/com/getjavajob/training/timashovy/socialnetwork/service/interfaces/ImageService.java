package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import java.io.InputStream;

public interface ImageService {

    boolean upload(Long accountId, InputStream avatarInputStream);

    boolean delete(Long id);

    InputStream get(Long accountId);

    boolean update(Long accountId, InputStream imageInputStream);

}
