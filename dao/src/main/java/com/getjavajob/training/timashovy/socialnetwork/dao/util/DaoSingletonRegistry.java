package com.getjavajob.training.timashovy.socialnetwork.dao.util;

import java.util.HashMap;
import java.util.Map;

public class DaoSingletonRegistry {

    private static final DaoSingletonRegistry DAO_REGISTRY_INSTANCE = new DaoSingletonRegistry();
    private Map<String, Object> daoSingletons;

    private DaoSingletonRegistry() {
        daoSingletons = new HashMap<>();
    }

    public static DaoSingletonRegistry getDaoRegistryInstance() {
        return DAO_REGISTRY_INSTANCE;
    }

    public void registerSingleton(String key, Object singleton) {
        daoSingletons.put(key, singleton);
    }

    public Object getSingleton(String key) {
        return daoSingletons.get(key);
    }

}
