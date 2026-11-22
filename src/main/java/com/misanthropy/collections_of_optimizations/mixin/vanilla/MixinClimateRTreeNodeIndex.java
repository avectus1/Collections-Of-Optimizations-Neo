package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.core.FlatClimateNode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(targets = {"net.minecraft.world.level.biome.Climate$RTree$Node"},
   remap = false
)
public abstract class MixinClimateRTreeNodeIndex implements FlatClimateNode {
   @Unique
   private int coo$flatIndex = -1;

   @Override
   public int coo$flatIndex() {
      return this.coo$flatIndex;
   }

   @Override
   public void coo$setFlatIndex(int index) {
      this.coo$flatIndex = index;
   }
}
