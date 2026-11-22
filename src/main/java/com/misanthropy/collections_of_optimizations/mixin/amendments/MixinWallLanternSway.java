package com.misanthropy.collections_of_optimizations.mixin.amendments;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.mehvahdjukaar.amendments.common.block.WallLanternBlock;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(
   value = {WallLanternBlock.class},
   remap = false
)
public abstract class MixinWallLanternSway {
   @WrapWithCondition(
      method = {"entityInside"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/mehvahdjukaar/moonlight/api/platform/network/NetworkHelper;sendToAllClientPlayersTrackingEntity(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;)V"
      )},
      require = 0
   )
   private boolean coo$skipIdleSwaySync(Entity entity, CustomPacketPayload message) {
      return !CoOConfig.amendmentsSkipIdleSwaySync
         ? true
         : entity.xo != entity.getX() || entity.yo != entity.getY() || entity.zo != entity.getZ();
   }
}
