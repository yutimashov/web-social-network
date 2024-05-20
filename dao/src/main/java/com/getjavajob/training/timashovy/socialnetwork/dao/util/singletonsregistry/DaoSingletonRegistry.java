package com.getjavajob.training.timashovy.socialnetwork.dao.util.singletonsregistry;

import java.util.HashMap;
import java.util.Map;

public class DaoSingletonRegistry implements SingletonRegistry {

    private static final DaoSingletonRegistry DAO_REGISTRY_INSTANCE = new DaoSingletonRegistry();
    private final Map<String, Object> daoSingletons;

    private DaoSingletonRegistry() {
        daoSingletons = new HashMap<>();
    }

    public static DaoSingletonRegistry getDaoRegistryInstance() {
        return DAO_REGISTRY_INSTANCE;
    }

    @Override
    public synchronized <T> void registerSingleton(String key, T singleton) {
        daoSingletons.put(key, singleton);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getSingleton(String key) {
        return (T) daoSingletons.get(key);
    }

}
