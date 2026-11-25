package com.misanthropy.collections_of_optimizations.mixin.brutecull;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.BfcFramebufferBinds;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.evandev.brute_force_culling.culling.CullingStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/** Drops the two redundant framebuffer switches around each clear-then-bind in CullingStateManager (see {@link BfcFramebufferBinds}). */
@Mixin(value = CullingStateManager.class, remap = false)
public abstract class MixinBfcDepthMapBinds {
    @WrapOperation(
            method = "/^lambda\\$updateDepthMap\\$\\d+$/",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderTarget;clear(Z)V"),
            require = 0
    )
    private static void coo$clearKeepBound(RenderTarget target, boolean clearError, Operation<Void> original) {
        BfcFramebufferBinds.leftBound = null;
        if (!CoOConfig.bfcSkipRedundantBinds || !RenderSystem.isOnRenderThread()) {
            original.call(target, clearError);
            return;
        }
        BfcFramebufferBinds.keepBoundInClear = true;
        try {
            original.call(target, clearError);
        } finally {
            BfcFramebufferBinds.keepBoundInClear = false;
        }
    }

    @WrapOperation(
            method = "/^lambda\\$updateDepthMap\\$\\d+$/",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderTarget;bindWrite(Z)V"),
            require = 0
    )
    private static void coo$skipRebind(RenderTarget target, boolean setViewport, Operation<Void> original) {
        RenderTarget bound = BfcFramebufferBinds.leftBound;
        BfcFramebufferBinds.leftBound = null;
        // The clear already bound this exact target and set its viewport; binding it again without
        // touching the viewport changes nothing.
        if (bound == target && !setViewport) {
            return;
        }
        original.call(target, setViewport);
    }
}
