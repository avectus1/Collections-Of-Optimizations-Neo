package com.misanthropy.collections_of_optimizations.mixin.brutecull;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.core.BfcFramebufferBinds;
import com.mojang.blaze3d.pipeline.RenderTarget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/** Lets a clear issued by Brute Force Culling leave its target bound (see {@link BfcFramebufferBinds}). */
@Mixin(value = RenderTarget.class, remap = false)
public abstract class MixinBfcRenderTargetClear {
    @WrapOperation(
            method = "clear(Z)V",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderTarget;unbindWrite()V"),
            require = 0
    )
    private void coo$keepBoundForBfc(RenderTarget target, Operation<Void> original) {
        if (BfcFramebufferBinds.keepBoundInClear) {
            BfcFramebufferBinds.leftBound = target;
            return;
        }
        original.call(target);
    }
}
