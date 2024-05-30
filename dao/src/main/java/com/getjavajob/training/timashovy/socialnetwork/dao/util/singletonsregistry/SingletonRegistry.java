package com.getjavajob.training.timashovy.socialnetwork.dao.util.singletonsregistry;

public interface SingletonRegistry {

    <T> void addSingleton(String singletonName, T singleton);

    <T> T getSingleton(String singletonName);

}
