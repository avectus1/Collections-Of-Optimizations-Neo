package com.misanthropy.collections_of_optimizations.mixin.arsnouveau;

import com.hollingsworth.arsnouveau.common.light.LightManager;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = {LevelRenderer.class}, remap = false)
public abstract class MixinArsDynamicLightColor {
   @ModifyReturnValue(
      method = {"getLightColor(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)I"},
      at = {@At("TAIL")}
   )
   private static int coo$arsDynamicLight(int original, @Local(argsOnly = true) BlockAndTintGetter level, @Local(argsOnly = true) BlockPos pos) {
      return LightManager.shouldUpdateDynamicLight() && !level.getBlockState(pos).isSolidRender(level, pos)
         ? LightManager.getLightmapWithDynamicLight(pos, original)
         : original;
   }
}
