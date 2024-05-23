package com.getjavajob.training.timashovy.socialnetwork.dao.util.singletonsregistry;

public interface SingletonRegistry {

    <T> void registerSingleton(String key, T singleton);

    <T> T getSingleton(String key);

}
