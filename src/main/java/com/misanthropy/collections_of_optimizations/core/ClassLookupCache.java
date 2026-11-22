package com.misanthropy.collections_of_optimizations.core;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import java.util.concurrent.ConcurrentHashMap;

public final class ClassLookupCache {
    private static final Object MISSING = new Object();
    private static final ConcurrentHashMap<String, Object> RESULTS = new ConcurrentHashMap<>();

    private ClassLookupCache() {
    }

    public static Class<?> lookup(String name, Operation<Class<?>> original) throws ClassNotFoundException {
        Object cached = RESULTS.get(name);
        if (cached instanceof Class<?> type) {
            return type;
        }
        if (cached == MISSING) {
            throw Missing.INSTANCE;
        }
        try {
            Class<?> type = original.call(name);
            RESULTS.put(name, type);
            return type;
        } catch (Throwable t) {
            RESULTS.put(name, MISSING);
            throw Missing.INSTANCE;
        }
    }

    private static final class Missing extends ClassNotFoundException {
        static final Missing INSTANCE = new Missing();

        private Missing() {
            super("class not present (cached)");
        }

        @Override
        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }
}
