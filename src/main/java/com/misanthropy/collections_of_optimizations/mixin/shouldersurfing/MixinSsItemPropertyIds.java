package com.misanthropy.collections_of_optimizations.mixin.shouldersurfing;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.github.exopandora.shouldersurfing.client.event.handler.ComputePlayerAimStateEventHandlerImpl;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/** The adaptive crosshair also re-parses each configured item property id on every check. Parse each once. */
@Mixin(value = ComputePlayerAimStateEventHandlerImpl.class, remap = false)
public abstract class MixinSsItemPropertyIds {
    @Unique
    private static final ConcurrentHashMap<String, ResourceLocation> coo$IDS = new ConcurrentHashMap<>();

    @Redirect(method = "isAdaptiveItemStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;parse(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;"))
    private static ResourceLocation coo$parseOnce(String id) {
        if (!CoOConfig.shouldersurfingPatternMemo) {
            return ResourceLocation.parse(id);
        }
        ResourceLocation cached = coo$IDS.get(id);
        if (cached == null) {
            cached = ResourceLocation.parse(id);
            if (coo$IDS.size() < 1024) {
                coo$IDS.put(id, cached);
            }
        }
        return cached;
    }
}
