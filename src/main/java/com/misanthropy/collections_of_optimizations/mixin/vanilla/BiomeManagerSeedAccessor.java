package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import net.minecraft.world.level.biome.BiomeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BiomeManager.class, remap = false)
public interface BiomeManagerSeedAccessor {

    @Accessor("biomeZoomSeed")
    long coo$biomeZoomSeed();
}
