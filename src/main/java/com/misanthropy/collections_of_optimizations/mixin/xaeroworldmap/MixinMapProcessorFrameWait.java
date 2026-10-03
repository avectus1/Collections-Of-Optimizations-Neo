package com.misanthropy.collections_of_optimizations.mixin.xaeroworldmap;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.map.MapProcessor;

import java.util.concurrent.locks.LockSupport;

@Mixin(value = MapProcessor.class, remap = false)
public abstract class MixinMapProcessorFrameWait {

    @Shadow
    private long renderStartTime;

    @Shadow
    public abstract boolean actuallySkippingWorldRender();

    @Inject(method = "resetRenderStartTime()V", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$idleMapFrameWait(CallbackInfo ci) {
        if (!CoOConfig.xaeroworldmapIdleMapFrameWait || !this.actuallySkippingWorldRender()) {
            return;
        }

        long deadline = this.renderStartTime + 1_600_000L;
        long spinTail = CoOConfig.xaeroworldmapMapFrameSpinTail * 1_000L;
        long remaining = deadline - System.nanoTime();
        while (remaining > 0L) {
            if (remaining > spinTail) {
                LockSupport.parkNanos(remaining - spinTail);
            } else {
                Thread.onSpinWait();
            }
            remaining = deadline - System.nanoTime();
        }

        this.renderStartTime = -1L;
        ci.cancel();
    }
}
