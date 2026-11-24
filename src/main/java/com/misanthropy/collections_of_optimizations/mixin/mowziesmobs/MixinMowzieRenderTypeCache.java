package com.misanthropy.collections_of_optimizations.mixin.mowziesmobs;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mowzie's glow and solar flare effects build a brand new render type every frame for every glowing thing. Each new
 * one also counts as a different render type, which forces the game to flush what it was drawing. Build one per
 * texture and reuse it. Looks the same.
 */
@Mixin(value = MMRenderType.class, remap = false)
public abstract class MixinMowzieRenderTypeCache {
    @Unique
    private static final ConcurrentHashMap<ResourceLocation, RenderType> coo$GLOW = new ConcurrentHashMap<>();
    @Unique
    private static final ConcurrentHashMap<ResourceLocation, RenderType> coo$FLARE = new ConcurrentHashMap<>();

    @Inject(method = "getGlowingEffect", at = @At("HEAD"), cancellable = true)
    private static void coo$glowCached(ResourceLocation texture, CallbackInfoReturnable<RenderType> cir) {
        RenderType cached = CoOConfig.mowziesmobsRenderTypeCache ? coo$GLOW.get(texture) : null;
        if (cached != null) {
            cir.setReturnValue(cached);
        }
    }

    @Inject(method = "getGlowingEffect", at = @At("RETURN"))
    private static void coo$glowStore(ResourceLocation texture, CallbackInfoReturnable<RenderType> cir) {
        if (CoOConfig.mowziesmobsRenderTypeCache && texture != null && coo$GLOW.size() < 256) {
            coo$GLOW.putIfAbsent(texture, cir.getReturnValue());
        }
    }

    @Inject(method = "getSolarFlare", at = @At("HEAD"), cancellable = true)
    private static void coo$flareCached(ResourceLocation texture, CallbackInfoReturnable<RenderType> cir) {
        RenderType cached = CoOConfig.mowziesmobsRenderTypeCache ? coo$FLARE.get(texture) : null;
        if (cached != null) {
            cir.setReturnValue(cached);
        }
    }

    @Inject(method = "getSolarFlare", at = @At("RETURN"))
    private static void coo$flareStore(ResourceLocation texture, CallbackInfoReturnable<RenderType> cir) {
        if (CoOConfig.mowziesmobsRenderTypeCache && texture != null && coo$FLARE.size() < 256) {
            coo$FLARE.putIfAbsent(texture, cir.getReturnValue());
        }
    }
}
