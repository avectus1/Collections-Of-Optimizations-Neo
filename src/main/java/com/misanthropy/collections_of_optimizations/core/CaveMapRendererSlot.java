package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.resources.ResourceLocation;

public interface CaveMapRendererSlot {
    long coo$lastUsed();

    void coo$touch(long now);

    ResourceLocation coo$textureId();
}
