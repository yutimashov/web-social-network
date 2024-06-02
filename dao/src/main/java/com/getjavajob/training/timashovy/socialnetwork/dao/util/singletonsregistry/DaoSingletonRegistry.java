package com.getjavajob.training.timashovy.socialnetwork.dao.util.singletonsregistry;

import java.util.HashMap;
import java.util.Map;

public class DaoSingletonRegistry implements SingletonRegistry {

    private final Map<String, Object> singletons;

    public DaoSingletonRegistry() {
        singletons = new HashMap<>();
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
