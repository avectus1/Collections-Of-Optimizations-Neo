package com.misanthropy.collections_of_optimizations.mixin.balm;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.blay09.mods.balm.api.network.ConfigReflection;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(value = ConfigReflection.class, remap = false)
public abstract class MixinConfigReflection {

    @Unique
    private static final Map<Class<?>, ResourceLocation> COO$IDENTIFIERS = new ConcurrentHashMap<>();

    @Inject(
            method = "getIdentifier(Ljava/lang/Class;)Lnet/minecraft/resources/ResourceLocation;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$cachedIdentifier(Class<?> clazz, CallbackInfoReturnable<ResourceLocation> cir) {
        if (CoOConfig.balmCacheConfigIdentifier) {
            ResourceLocation cached = COO$IDENTIFIERS.get(clazz);
            if (cached != null) {
                cir.setReturnValue(cached);
            }
        }
    }

    @Inject(
            method = "getIdentifier(Ljava/lang/Class;)Lnet/minecraft/resources/ResourceLocation;",
            at = @At("RETURN"),
            require = 0
    )
    private static void coo$rememberIdentifier(Class<?> clazz, CallbackInfoReturnable<ResourceLocation> cir) {
        if (CoOConfig.balmCacheConfigIdentifier) {
            COO$IDENTIFIERS.putIfAbsent(clazz, cir.getReturnValue());
        }
    }
}
