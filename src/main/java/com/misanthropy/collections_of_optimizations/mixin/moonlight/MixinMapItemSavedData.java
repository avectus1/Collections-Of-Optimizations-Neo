package com.misanthropy.collections_of_optimizations.mixin.moonlight;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.mehvahdjukaar.moonlight.api.map.ExpandedMapData;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MapItemSavedData.class, priority = 1500, remap = false)
public abstract class MixinMapItemSavedData {
    @TargetHandler(mixin = "net.mehvahdjukaar.moonlight.core.mixins.MapDataMixin", name = "checkCustomDeco")
    @WrapMethod(method = "@MixinSquared:Handler", require = 0)
    private void coo$skipEmptyMapMarkerScan(BlockGetter world, int x, int z, CallbackInfo ci, Operation<Void> original) {
        if (CoOConfig.moonlightSkipEmptyMapMarkerScan && ((ExpandedMapData) this).ml$getCustomMarkers().isEmpty()) {
            return;
        }
        original.call(world, x, z, ci);
    }
}
