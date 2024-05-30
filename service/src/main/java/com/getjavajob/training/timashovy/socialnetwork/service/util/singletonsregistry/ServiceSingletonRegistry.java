package com.getjavajob.training.timashovy.socialnetwork.service.util.singletonsregistry;

import com.getjavajob.training.timashovy.socialnetwork.dao.util.singletonsregistry.SingletonRegistry;

import java.util.HashMap;
import java.util.Map;

public class ServiceSingletonRegistry implements SingletonRegistry {

    private static final ServiceSingletonRegistry INSTANCE = new ServiceSingletonRegistry();
    private final Map<String, Object> singletons = new HashMap<>();

    private ServiceSingletonRegistry() {}

    public static ServiceSingletonRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    public synchronized <T> void addSingleton(String key, T singleton) {
        if (!singletons.containsKey(key)) {
            singletons.put(key, singleton);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getSingleton(String key) {
        return (T) singletons.get(key);
    }

}
