package com.misanthropy.collections_of_optimizations.mixin.lootr;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import noobanidus.mods.lootr.common.block.entity.BlockEntityTicker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

/**
 * Only skips Lootr's conversion pass when it has nothing queued. Every waiting chest is still checked every
 * tick exactly like stock Lootr; capping or spacing out that pass left chests in new structures unconverted.
 */
@Mixin(value = BlockEntityTicker.class, remap = false)
public abstract class MixinBlockEntityTicker {
    @Shadow
    @Final
    private Map<?, ?> blockEntityEntries;

    @Shadow
    @Final
    private Map<?, ?> pendingEntries;

    @Inject(method = "onServerLevelTick", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$skipIdlePass(CallbackInfo ci) {
        if (!CoOConfig.lootrSkipIdleTileTicker || !this.blockEntityEntries.isEmpty()) {
            return;
        }
        synchronized (this.pendingEntries) {
            if (this.pendingEntries.isEmpty()) {
                ci.cancel();
            }
        }
    }
}
