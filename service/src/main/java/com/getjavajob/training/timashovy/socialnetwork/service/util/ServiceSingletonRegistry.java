package com.getjavajob.training.timashovy.socialnetwork.service.util;

import java.util.HashMap;
import java.util.Map;

public class ServiceSingletonRegistry implements SingletonRegistry {

    private static final ServiceSingletonRegistry INSTANCE = new ServiceSingletonRegistry();
    private final Map<String, Object> singletons = new HashMap<>();

    private ServiceSingletonRegistry() {}

    public static ServiceSingletonRegistry getServiceSingletonRegistry() {
        return INSTANCE;
    }

    @Override
    public synchronized <T> void registerSingleton(String key, T singleton) {
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
