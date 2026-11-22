package com.misanthropy.collections_of_optimizations.mixin.lodestone;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.mojang.blaze3d.pipeline.RenderTarget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import team.lodestar.lodestone.handlers.LodestoneRenderHandler;
import team.lodestar.lodestone.systems.rendering.buffer.LodestoneRenderLayer;

/**
 * Lodestone blits the whole depth buffer into its own copy every frame so its deferred effects can
 * fade against terrain, even on frames where none of those effects were queued. Only copy when one of
 * its deferred layers actually has something to draw this frame.
 */
@Mixin(value = LodestoneRenderHandler.class, remap = false)
public abstract class MixinLodestoneDepthCopy {
    @WrapOperation(
            method = "render",
            at = @At(value = "INVOKE", target = "Lteam/lodestar/lodestone/handlers/LodestoneRenderHandler;copyDepthBuffer(Lcom/mojang/blaze3d/pipeline/RenderTarget;)V"),
            require = 0)
    private static void coo$copyOnlyWhenNeeded(RenderTarget target, Operation<Void> original) {
        if (CoOConfig.lodestoneSkipIdleDepthCopy
                && coo$empty(LodestoneRenderHandler.DEFERRED_RENDER)
                && coo$empty(LodestoneRenderHandler.LATE_DEFERRED_RENDER)) {
            return;
        }
        original.call(target);
    }

    private static boolean coo$empty(LodestoneRenderLayer layer) {
        if (layer == null) {
            return true;
        }
        return coo$empty(layer.getTarget()) && coo$empty(layer.getParticleTarget()) && coo$empty(layer.getTrailTarget());
    }

    private static boolean coo$empty(Object source) {
        return !(source instanceof BufferSourceStartedAccessor accessor) || accessor.coo$startedBuilders().isEmpty();
    }
}
