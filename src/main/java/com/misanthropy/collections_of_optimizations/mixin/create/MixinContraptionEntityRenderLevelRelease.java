package com.misanthropy.collections_of_optimizations.mixin.create;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ContraptionRenderLevelRelease;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.Contraption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {AbstractContraptionEntity.class},
   remap = false
)
public abstract class MixinContraptionEntityRenderLevelRelease {
   @Shadow
   protected Contraption contraption;

   @Inject(
      method = {"onRemovedFromLevel"},
      at = {@At("TAIL")},
      require = 1
   )
   private void coo$releaseRenderLevel(CallbackInfo ci) {
      if (CoOConfig.createReleaseContraptionRenderLevels && this.contraption != null && ((AbstractContraptionEntity)(Object)this).level().isClientSide()) {
         Object client = ((ContraptionClientAccessor)this.contraption).coo$clientContraption().get();
         if (client != null) {
            ContraptionRenderLevelRelease.release(client);
         }
      }
   }
}
