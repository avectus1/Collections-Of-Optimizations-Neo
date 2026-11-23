package com.misanthropy.collections_of_optimizations.mixin.alexscaves;

import com.github.alexmodguy.alexscaves.client.render.misc.CaveMapRenderer;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.misanthropy.collections_of_optimizations.core.CaveMapRendererSlot;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Alex's Caves draws cave maps with one renderer (and one 128x128 texture) per map item object, kept in a
 * static map forever. Item objects are replaced all the time (inventory syncs, item frames reloading with
 * their chunk...), so a new renderer and texture pile up each time. Remember when each renderer was last
 * asked for, so stale ones can be released; asking again simply makes a new one.
 */
@Mixin(value = CaveMapRenderer.class, remap = false)
public abstract class MixinCaveMapRendererRelease implements CaveMapRendererSlot {
    @Unique
    private volatile long coo$lastUsed = Util.getMillis();
    @Unique
    private ResourceLocation coo$textureId;

    @ModifyExpressionValue(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;register(Ljava/lang/String;Lnet/minecraft/client/renderer/texture/DynamicTexture;)Lnet/minecraft/resources/ResourceLocation;"))
    private ResourceLocation coo$keepTextureId(ResourceLocation id) {
        this.coo$textureId = id;
        return id;
    }

    @ModifyReturnValue(method = "getMapFor", at = @At("RETURN"))
    private static CaveMapRenderer coo$touchOnUse(CaveMapRenderer renderer) {
        if (renderer != null) {
            ((CaveMapRendererSlot) renderer).coo$touch(Util.getMillis());
        }
        return renderer;
    }

    @Override
    public long coo$lastUsed() {
        return this.coo$lastUsed;
    }

    @Override
    public void coo$touch(long now) {
        this.coo$lastUsed = now;
    }

    @Override
    public ResourceLocation coo$textureId() {
        return this.coo$textureId;
    }
}
