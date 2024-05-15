package com.getjavajob.training.timashovy.socialnetwork.service.util;

public interface SingletonRegistry {

    <T> void registerSingleton(String key, T singleton);

    <T> T getSingleton(String key);

}
