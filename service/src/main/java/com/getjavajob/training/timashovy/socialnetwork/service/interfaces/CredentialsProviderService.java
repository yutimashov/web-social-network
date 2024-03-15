package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

public interface CredentialsProviderService<T> {

    Long create(T credential);

    boolean verify(T verifyingCredential);

    boolean update(T credential);

}
