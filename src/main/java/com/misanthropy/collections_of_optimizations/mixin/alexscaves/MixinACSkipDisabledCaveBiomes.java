package com.misanthropy.collections_of_optimizations.mixin.alexscaves;

import com.bawnorton.mixinsquared.TargetHandler;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CaveBiomeGate;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.Climate.Sampler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {MultiNoiseBiomeSource.class},
   priority = 1500,
   remap = false
)
public abstract class MixinACSkipDisabledCaveBiomes {
   @TargetHandler(
      mixin = "com.github.alexmodguy.alexscaves.mixin.MultiNoiseBiomeSourceMixin",
      name = "ac_getNoiseBiomeCoords"
   )
   @WrapMethod(method = "@MixinSquared:Handler", require = 0)
   private void coo$skipWhenAllCaveBiomesDisabled(int x, int y, int z, Sampler sampler, CallbackInfoReturnable<Holder<Biome>> cir, Operation<Void> original) {
      if (CoOConfig.alexscavesSkipDisabledBiomeHook && CaveBiomeGate.allDisabled) {
         return;
      }
      original.call(x, y, z, sampler, cir);
   }
}
