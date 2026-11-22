package com.misanthropy.collections_of_optimizations.mixin.ironsspellbooks;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.IsbSyncedDataRegistry;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(
   targets = {"io.redspace.ironsspellbooks.player.ClientPlayerEvents"},
   remap = false
)
public abstract class MixinIsbStaleSyncedDataPurge {
   @Inject(
      method = {"onPlayerLogOut"},
      at = {@At("HEAD")},
      require = 0
   )
   private static void coo$purgeOnLogout(LoggingOut event, CallbackInfo ci) {
      if (CoOConfig.ironsspellbooksClearStaleSyncedData) {
         IsbSyncedDataRegistry.purge();
      }
   }
}
