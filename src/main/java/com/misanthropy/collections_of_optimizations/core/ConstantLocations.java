package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.ConcurrentHashMap;

public final class ConstantLocations {

    private static final ConcurrentHashMap<String, ResourceLocation> BY_PATH = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, ResourceLocation> BY_TEXT = new ConcurrentHashMap<>();

    public static ResourceLocation parse(String location) {
        ResourceLocation cached = BY_TEXT.get(location);
        if (cached != null) {
            return cached;
        }
        ResourceLocation created = ResourceLocation.parse(location);
        if (BY_TEXT.size() < 4096) {
            BY_TEXT.put(location, created);
        }
        return created;
    }

    private ConstantLocations() {
    }

    public static ResourceLocation of(String namespace, String path) {
        ResourceLocation cached = BY_PATH.get(path);
        if (cached != null && cached.getNamespace().equals(namespace)) {
            return cached;
        }
        ResourceLocation created = ResourceLocation.fromNamespaceAndPath(namespace, path);
        BY_PATH.put(path, created);
        return created;
    }
}
