package com.misanthropy.collections_of_optimizations.mixin.brutecull;

import com.evandev.brute_force_culling.culling.CullingStateManager;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Root cause of the "chunks vanish while something glows" bug: vanilla's glowing outline effect runs right
 * before Brute Force Culling copies the depth buffer, and its last pass leaves GL blending on. BFC's copy
 * shader writes packed depth into all four color channels, alpha included, so with blending on every depth
 * value is mixed with the cleared target by its own alpha. The stored depths come out far too close, terrain
 * looks hidden behind a wall that isn't there, and BFC culls it. Blending is turned off for BFC's depth copy
 * and culling-map draws, then put back the way it was.
 */
@Mixin(value = CullingStateManager.class, remap = false)
public abstract class MixinBfcBlendState {
    @Unique
    private static boolean coo$blendWasOn;

    @Inject(method = "onProfilerPopPush", at = @At("HEAD"), require = 0)
    private static void coo$noBlendHead(String stage, CallbackInfo ci) {
        if (CoOConfig.bfcFixBlendState && "destroyProgress".equals(stage)) {
            coo$blendWasOn = GL11.glIsEnabled(GL11.GL_BLEND);
            if (coo$blendWasOn) {
                RenderSystem.disableBlend();
            }
        }
    }

    @Inject(method = "onProfilerPopPush", at = @At("RETURN"), require = 0)
    private static void coo$noBlendReturn(String stage, CallbackInfo ci) {
        if (coo$blendWasOn && "destroyProgress".equals(stage)) {
            coo$blendWasOn = false;
            RenderSystem.enableBlend();
        }
    }
}
