package com.misanthropy.collections_of_optimizations.mixin.brutecull;

import com.evandev.brute_force_culling.culling.CullingStateManager;
import com.misanthropy.collections_of_optimizations.core.BfcGlowGuard;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * While glowing outlines are on screen, Brute Force Culling's chunk and entity culling is bypassed so it
 * cannot hide terrain or mobs around a glowing entity. Everything else about the mod is unchanged.
 */
@Mixin(value = CullingStateManager.class, remap = false)
public abstract class MixinBfcGlowNoCull {
    @Inject(method = "shouldRenderChunk", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$drawChunksWhileGlowing(CallbackInfoReturnable<Boolean> cir) {
        if (BfcGlowGuard.active()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "shouldSkipEntity", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$drawEntitiesWhileGlowing(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (BfcGlowGuard.active()) {
            cir.setReturnValue(false);
        }
    }
}
