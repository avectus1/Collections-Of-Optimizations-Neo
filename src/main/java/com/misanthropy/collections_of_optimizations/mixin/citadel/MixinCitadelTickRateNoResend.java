package com.misanthropy.collections_of_optimizations.mixin.citadel;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTickRateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Citadel resets the server tick rate at the start of every tick, even when nothing changed. Each reset
 * recomputes the tick timing and sends a ticking-state packet to every player, 20 times a second. Skip
 * the call when the rate is already the one being set.
 */
@Mixin(value = MinecraftServer.class, priority = 1100, remap = false)
public abstract class MixinCitadelTickRateNoResend {
    @Redirect(method = "setGlobalTickLengthMs", require = 0,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/ServerTickRateManager;setTickRate(F)V"))
    private void coo$setIfChanged(ServerTickRateManager manager, float rate) {
        if (CoOConfig.citadelSkipSameTickRate && manager.tickrate() == rate) {
            return;
        }
        manager.setTickRate(rate);
    }
}
