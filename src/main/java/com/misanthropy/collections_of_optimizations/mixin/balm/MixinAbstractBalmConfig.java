package com.misanthropy.collections_of_optimizations.mixin.balm;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.blay09.mods.balm.api.network.ConfigReflection;
import net.blay09.mods.balm.common.config.AbstractBalmConfig;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(value = AbstractBalmConfig.class, remap = false)
public abstract class MixinAbstractBalmConfig {

    @Shadow
    @Final
    private Map<ResourceLocation, Object> activeReflectionConfigs;

    @Inject(method = "getActiveConfig(Ljava/lang/Class;)Ljava/lang/Object;", at = @At("HEAD"), cancellable = true, require = 0)
    @SuppressWarnings("unchecked")
    private <T> void coo$activeConfigHitFirst(Class<T> clazz, CallbackInfoReturnable<T> cir) {
        if (!CoOConfig.balmCacheConfigIdentifier) {
            return;
        }
        Object hit = this.activeReflectionConfigs.get(ConfigReflection.getIdentifier(clazz));
        if (hit != null) {
            cir.setReturnValue((T) hit);
        }
    }
}
