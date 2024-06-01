package com.getjavajob.training.timashovy.socialnetwork.dao.util.singletonsregistry;

import java.util.HashMap;
import java.util.Map;

public class DaoSingletonRegistry implements SingletonRegistry {

    private final Map<String, Object> singletons;
    private static volatile DaoSingletonRegistry instance;

    private DaoSingletonRegistry() {
        singletons = new HashMap<>();
    }

    public static DaoSingletonRegistry getInstance() {
        if (instance == null) {
            synchronized (DaoSingletonRegistry.class) {
                if (instance == null) {
                    instance = new DaoSingletonRegistry();
                }
            }
        }
        return instance;
    }

    @Override
    public <T> void addSingleton(String key, T singleton) {
        if (!singletons.containsKey(key)) {
            synchronized (this) {
                if (!singletons.containsKey(key)) {
                    singletons.put(key, singleton);
                }
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getSingleton(String key) {
        return (T) singletons.get(key);
    }

}
