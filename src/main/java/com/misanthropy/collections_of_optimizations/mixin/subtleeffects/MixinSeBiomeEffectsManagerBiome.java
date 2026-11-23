package com.misanthropy.collections_of_optimizations.mixin.subtleeffects;

import com.misanthropy.collections_of_optimizations.core.SeBiomeMemo;
import einstein.subtle_effects.ticking.BiomeEffectsManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/** Shares the biome lookup of the block being animated (see MixinSeClientLevelBiome). */
@Mixin(value = BiomeEffectsManager.class, remap = false)
public abstract class MixinSeBiomeEffectsManagerBiome {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBiome(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/Holder;", ordinal = 0))
    private static Holder<Biome> coo$shared(Level level, BlockPos pos) {
        return level instanceof SeBiomeMemo memo ? memo.coo$animateBiome(pos) : level.getBiome(pos);
    }
}
