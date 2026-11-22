package com.misanthropy.collections_of_optimizations.mixin.kubejs;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import dev.latvian.mods.kubejs.client.highlight.HighlightRenderer;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * KubeJS' highlight renderer clears its world buffer, copies the depth buffer and runs its outline
 * shader every frame, even when nothing in the world is highlighted (the highlight key is not held).
 * Nothing it draws is visible then, so skip the world pass while there is nothing to highlight.
 */
@Mixin(value = HighlightRenderer.class, remap = false)
public abstract class MixinKubeHighlightIdle {
    @Shadow @Final public Reference2IntMap<Entity> highlightedEntities;
    @Shadow @Final public Long2IntMap highlightedBlocks;

    @Inject(method = "renderAfterLevel", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$skipIdleWorldPass(Minecraft mc, RenderLevelStageEvent event, CallbackInfo ci) {
        if (CoOConfig.kubejsSkipIdleHighlightPass && this.highlightedEntities.isEmpty() && this.highlightedBlocks.isEmpty()) {
            ci.cancel();
        }
    }

    @WrapOperation(
            method = "clearBuffers",
            at = @At(value = "INVOKE", target = "Ldev/latvian/mods/kubejs/client/highlight/HighlightRenderer$ShaderChain;clearInput(Lnet/minecraft/client/Minecraft;)V", ordinal = 0),
            require = 0)
    private void coo$skipIdleWorldClear(HighlightRenderer.ShaderChain chain, Minecraft mc, Operation<Void> original) {
        if (CoOConfig.kubejsSkipIdleHighlightPass && this.highlightedEntities.isEmpty() && this.highlightedBlocks.isEmpty()) {
            return;
        }
        original.call(chain, mc);
    }
}
