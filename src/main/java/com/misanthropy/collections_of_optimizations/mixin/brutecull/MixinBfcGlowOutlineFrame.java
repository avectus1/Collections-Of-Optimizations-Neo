package com.misanthropy.collections_of_optimizations.mixin.brutecull;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.core.BfcGlowGuard;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.PostChain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/** Notes each frame in which vanilla runs the glowing outline effect (only when a glowing entity was drawn). */
@Mixin(value = LevelRenderer.class, remap = false)
public abstract class MixinBfcGlowOutlineFrame {
    @Shadow
    private PostChain entityEffect;

    @WrapOperation(
            method = "renderLevel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/PostChain;process(F)V"),
            require = 0
    )
    private void coo$noteOutline(PostChain chain, float partialTick, Operation<Void> original) {
        if (chain == this.entityEffect) {
            BfcGlowGuard.outlineFrame();
        }
        original.call(chain, partialTick);
    }
}
