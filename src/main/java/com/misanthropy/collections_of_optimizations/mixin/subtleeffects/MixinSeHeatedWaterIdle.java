package com.misanthropy.collections_of_optimizations.mixin.subtleeffects;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import einstein.subtle_effects.util.ParticleSpawnUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Steaming and boiling water are off by default in Subtle Effects, but every water block the client animates still
 * reads light and nearby blocks (up to 27 lookups) for them. When both are off nothing can spawn, so skip the work.
 */
@Mixin(value = ParticleSpawnUtil.class, remap = false)
public abstract class MixinSeHeatedWaterIdle {
    @Inject(method = "spawnHeatedWaterParticles(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;ZDZZ)V", at = @At("HEAD"), cancellable = true)
    private static void coo$idle(Level level, BlockPos pos, RandomSource random, boolean falling, double height, boolean steam, boolean boiling, CallbackInfo ci) {
        if (!steam && !boiling && CoOConfig.subtleeffectsIdleWater) {
            ci.cancel();
        }
    }
}
