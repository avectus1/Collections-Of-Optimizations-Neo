package com.misanthropy.collections_of_optimizations.mixin.lithostitched;

import dev.worldgen.lithostitched.worldgen.structure.LithostitchedTemplates.WeightedEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(
   value = {WeightedEntry.class},
   remap = false
)
public interface LithostitchedWeightedEntryAccessor {
   @Accessor("weight")
   int coo$weight();
}
