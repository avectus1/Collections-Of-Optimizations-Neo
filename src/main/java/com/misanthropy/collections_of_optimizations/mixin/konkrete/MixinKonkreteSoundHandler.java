package com.misanthropy.collections_of_optimizations.mixin.konkrete;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import de.keksuccino.konkrete.sound.SoundHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Konkrete starts a thread at launch that wakes every 100 ms forever to watch the master volume, for its
 * deprecated sound API that almost nothing uses. Start it the first time a sound is actually registered.
 */
@Mixin(value = SoundHandler.class, remap = false)
public abstract class MixinKonkreteSoundHandler {
    @Unique
    private static boolean coo$pollerWanted;

    @Inject(method = "init", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$deferVolumePoller(CallbackInfo ci) {
        if (CoOConfig.konkreteDeferVolumeThread && !coo$pollerWanted) {
            ci.cancel();
        }
    }

    @Inject(method = "registerSound", at = @At("HEAD"), require = 0)
    private static void coo$startOnDemand(String key, String path, CallbackInfo ci) {
        if (!coo$pollerWanted) {
            coo$pollerWanted = true;
            SoundHandler.init();
        }
    }
}
