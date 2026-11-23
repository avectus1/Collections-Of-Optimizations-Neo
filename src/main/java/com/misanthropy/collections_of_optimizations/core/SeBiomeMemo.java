package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

/** Biome of the block the client is currently animating, shared by vanilla and Subtle Effects within one call. */
public interface SeBiomeMemo {
    Holder<Biome> coo$animateBiome(BlockPos pos);
}
