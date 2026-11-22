package com.misanthropy.collections_of_optimizations.mixin.iceberg;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ClassLookupCache;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Iceberg's armor layer hook looks up two AzureLib classes by name on every armor piece every frame.
 * Only one of them exists in any given AzureLib version, so the other lookup searches the classpath and
 * throws a fresh ClassNotFoundException each time. The answer never changes, so remember it.
 */
@Mixin(value = HumanoidArmorLayer.class, priority = 1500, remap = false)
public abstract class MixinIcebergArmorClassLookup {
    @TargetHandler(mixin = "com.anthonyhilyard.iceberg.mixin.azurelib.HumanoidArmorLayerMixin", name = "icebergStoreBufferSource")
    @WrapOperation(method = "@MixinSquared:Handler",
            at = @At(value = "INVOKE", target = "Ljava/lang/Class;forName(Ljava/lang/String;)Ljava/lang/Class;"),
            require = 0)
    private Class<?> coo$cachedLookup(String name, Operation<Class<?>> original) throws ClassNotFoundException {
        return CoOConfig.icebergCacheArmorClassLookup ? ClassLookupCache.lookup(name, original) : original.call(name);
    }

    @TargetHandler(mixin = "com.anthonyhilyard.iceberg.mixin.azurelibarmor.HumanoidArmorLayerMixin", name = "icebergStoreBufferSource")
    @WrapOperation(method = "@MixinSquared:Handler",
            at = @At(value = "INVOKE", target = "Ljava/lang/Class;forName(Ljava/lang/String;)Ljava/lang/Class;"),
            require = 0)
    private Class<?> coo$cachedLookupArmor(String name, Operation<Class<?>> original) throws ClassNotFoundException {
        return CoOConfig.icebergCacheArmorClassLookup ? ClassLookupCache.lookup(name, original) : original.call(name);
    }
}
