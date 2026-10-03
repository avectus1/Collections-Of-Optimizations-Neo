package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.resources.ResourceLocation;

import java.util.Set;

public interface CurioCachedModifierHolder {
    boolean coo$skipCachedPurgeOnce();

    void coo$pruneCachedModifiers(Set<ResourceLocation> liveModifiers);
}
