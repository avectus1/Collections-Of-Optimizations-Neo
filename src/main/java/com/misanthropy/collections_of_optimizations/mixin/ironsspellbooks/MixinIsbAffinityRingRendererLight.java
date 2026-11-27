package com.misanthropy.collections_of_optimizations.mixin.ironsspellbooks;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.mojang.blaze3d.platform.Lighting;
import io.redspace.ironsspellbooks.render.AffinityRingRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/** Leaves flat lighting on after drawing an affinity ring in the GUI; GuiGraphics restores 3D lighting itself. */
@Mixin(value = AffinityRingRenderer.class, remap = false)
public abstract class MixinIsbAffinityRingRendererLight {
    @WrapOperation(method = "renderByItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Lighting;setupFor3DItems()V"))
    private void coo$keepFlatLight(Operation<Void> original) {
        if (!CoOConfig.isbAffinityRingGuiLight) {
            original.call();
        }
    }
}
