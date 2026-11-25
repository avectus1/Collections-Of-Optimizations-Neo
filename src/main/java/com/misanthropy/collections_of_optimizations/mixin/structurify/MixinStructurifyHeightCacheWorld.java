package com.misanthropy.collections_of_optimizations.mixin.structurify;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.StructurifyHeightCacheEpoch;
import java.util.Map;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * The height cache is thread local and the worker threads outlive a world, so after leaving a world each
 * thread kept its entries, and through their keys the old world's chunk generator and random state, until
 * it happened to generate again. Empty a thread's cache the first time it is used after a server stops.
 * Also start each thread's map at the capped size instead of a 16384 entry table.
 */
@Mixin(targets = "com.faboslav.structurify.common.world.level.chunk.ChunkGeneratorHeightCache", remap = false)
public abstract class MixinStructurifyHeightCacheWorld {
    @Shadow
    @Final
    private static ThreadLocal<Map<?, Integer>> HEIGHT_CACHE;

    @Inject(method = "put", at = @At("HEAD"), require = 0)
    private static void coo$dropOldWorld(CallbackInfo ci) {
        coo$check();
    }

    @Inject(method = "get", at = @At("HEAD"), require = 0)
    private static void coo$dropOldWorldGet(CallbackInfoReturnable<Integer> cir) {
        coo$check();
    }

    @Unique
    private static void coo$check() {
        if (CoOConfig.structurifyHeightCacheWorldReset && StructurifyHeightCacheEpoch.isStale()) {
            HEIGHT_CACHE.get().clear();
        }
    }

    @ModifyConstant(method = "lambda$static$0", constant = @Constant(intValue = 16384), require = 0)
    private static int coo$initialSize(int original) {
        int size = CoOConfig.structurifyHeightCacheSize;
        return size > 0 ? Math.min(original, size) : original;
    }
}
