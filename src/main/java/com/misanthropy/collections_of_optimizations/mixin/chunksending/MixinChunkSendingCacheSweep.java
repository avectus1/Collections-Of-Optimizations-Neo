package com.misanthropy.collections_of_optimizations.mixin.chunksending;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Chunk Sending walks its whole WeakHashMap of chunk packet caches every server tick just to find the
 * ones older than five minutes. Sweeping once every few ticks finds the same entries a moment later.
 */
@Mixin(targets = "com.chunksending.event.EventHandler", remap = false)
public abstract class MixinChunkSendingCacheSweep {
    @Unique
    private static int coo$sweepCountdown;

    @Inject(method = "clearExpiredPacketCaches", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$sweepLessOften(CallbackInfo ci) {
        int interval = CoOConfig.chunksendingCacheSweepInterval;
        if (interval <= 1) {
            return;
        }
        if (--coo$sweepCountdown > 0) {
            ci.cancel();
            return;
        }
        coo$sweepCountdown = interval;
    }
}
