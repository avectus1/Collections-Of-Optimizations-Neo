package com.misanthropy.collections_of_optimizations.mixin.dummmmmmy;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ParticleTextBatch;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(
   targets = {"net.mehvahdjukaar.dummmmmmy.client.DamageNumberParticle"},
   remap = false
)
public abstract class MixinDummyDamageNumberBatch {
   @WrapOperation(
      method = {"render"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;endBatch()V"
      )},
      remap = false,
      require = 0
   )
   private void coo$batchNumberFlush(BufferSource buffer, Operation<Void> original) {
      if (!CoOConfig.dummmmmmyBatchDamageNumberFlush || !ParticleTextBatch.defer(buffer)) {
         original.call(new Object[]{buffer});
      }
   }
}
