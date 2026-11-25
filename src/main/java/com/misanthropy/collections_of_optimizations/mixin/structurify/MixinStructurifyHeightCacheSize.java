package com.misanthropy.collections_of_optimizations.mixin.structurify;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Structurify keeps a height cache of 16384 entries on every world generation thread. With C2ME's many
 * worker threads that is tens of megabytes, while lookups for one structure land close together. Cap each
 * thread's cache at the configured size.
 */
@Mixin(targets = "com.faboslav.structurify.common.world.level.chunk.ChunkGeneratorHeightCache$1", remap = false)
public abstract class MixinStructurifyHeightCacheSize {
    @ModifyConstant(method = "removeEldestEntry", constant = @Constant(intValue = 16384), require = 0)
    private int coo$cacheSize(int original) {
        int size = CoOConfig.structurifyHeightCacheSize;
        return size > 0 ? size : original;
    }
}
