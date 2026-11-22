package com.misanthropy.collections_of_optimizations.mixin.xaeroworldmap;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.XaeroEmptyHeights;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xaero.map.misc.ConsistentBitArray;
import xaero.map.region.texture.RegionTexture;

@Mixin(
   value = {RegionTexture.class},
   remap = false
)
public abstract class MixinRegionTextureLazyHeights {
   @Redirect(
      method = {"<init>"},
      at = @At(
         value = "NEW",
         target = "(II)Lxaero/map/misc/ConsistentBitArray;"
      ),
      require = 0
   )
   private ConsistentBitArray coo$sharedEmptyHeights(int bitsPerEntry, int entries) {
      return CoOConfig.xaeroworldmapLazyTileHeights && bitsPerEntry == 13 && entries == 4096
         ? new ConsistentBitArray(bitsPerEntry, entries, XaeroEmptyHeights.SHARED)
         : new ConsistentBitArray(bitsPerEntry, entries);
   }
}
