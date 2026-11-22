package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import net.minecraft.world.level.biome.Climate.Parameter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = {"net.minecraft.world.level.biome.Climate$RTree$Node"},
   remap = false
)
public interface ClimateRTreeNodeAccessor {
   @Accessor("parameterSpace")
   Parameter[] coo$parameterSpace();
}
