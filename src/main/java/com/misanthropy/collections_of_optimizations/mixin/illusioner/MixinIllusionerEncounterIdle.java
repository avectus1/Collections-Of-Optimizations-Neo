package com.misanthropy.collections_of_optimizations.mixin.illusioner;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * The encounter manager copies its encounter list into a new list every server tick, even when no
 * Illusioner fight is running and nobody is waiting to be returned. With both empty the tick does nothing.
 */
@Mixin(targets = "com.fqkeh.illusioner.encounter.EncounterManager", remap = false)
public abstract class MixinIllusionerEncounterIdle {
    @Shadow @Final private Map<UUID, ?> encounters;
    @Shadow @Final private Set<UUID> pendingRecovery;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$skipIdleTick(MinecraftServer server, CallbackInfo ci) {
        if (CoOConfig.illusionerSkipIdleEncounterTick && this.encounters.isEmpty() && this.pendingRecovery.isEmpty()) {
            ci.cancel();
        }
    }
}
