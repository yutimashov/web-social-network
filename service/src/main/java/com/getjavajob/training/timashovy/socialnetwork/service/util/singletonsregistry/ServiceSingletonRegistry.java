package com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry;

import com.getjavajob.training.timashovy.socialnetwork.dao.util.singletonsregistry.SingletonRegistry;

import java.util.HashMap;
import java.util.Map;

public class ServiceSingletonRegistry implements SingletonRegistry {

    private final Map<String, Object> singletons;
    private static volatile ServiceSingletonRegistry instance;

    private ServiceSingletonRegistry() {
        singletons = new HashMap<>();
    }

    public static ServiceSingletonRegistry getInstance() {
        if (instance == null) {
            synchronized (ServiceSingletonRegistry.class) {
                instance = new ServiceSingletonRegistry();
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
