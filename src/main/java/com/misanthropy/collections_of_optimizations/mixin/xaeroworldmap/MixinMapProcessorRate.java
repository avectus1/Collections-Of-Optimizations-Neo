package com.misanthropy.collections_of_optimizations.mixin.xaeroworldmap;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.XaeroWorldMapScreenCheck;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.map.MapProcessor;

@Mixin(value = MapProcessor.class, remap = false)
public abstract class MixinMapProcessorRate {

    @Unique
    private long coo$lastRenderProcess;

    @Inject(method = "onRenderProcess(Lnet/minecraft/client/Minecraft;)V", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$capRenderProcessRate(Minecraft mc, CallbackInfo ci) {
        int interval = CoOConfig.xaeroworldmapRenderProcessInterval;
        if (interval <= 0 || XaeroWorldMapScreenCheck.isMapScreen(mc.screen)) {
            return;
        }

        long now = System.nanoTime() / 1_000_000L;
        if (now - this.coo$lastRenderProcess < interval) {
            ci.cancel();
            return;
        }
        this.coo$lastRenderProcess = now;
    }
}
