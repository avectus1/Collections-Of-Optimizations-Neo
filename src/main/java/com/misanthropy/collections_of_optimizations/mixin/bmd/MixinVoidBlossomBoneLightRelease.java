package com.misanthropy.collections_of_optimizations.mixin.bmd;

import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.VoidBlossomBoneLight;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.VoidBlossomEntity;
import com.misanthropy.collections_of_optimizations.core.ClientUnloadRelease;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** The renderer's light helper keeps the last boss it drew, and with it the old client world; let it go on unload. */
@Mixin(value = VoidBlossomBoneLight.class, remap = false)
public abstract class MixinVoidBlossomBoneLightRelease implements ClientUnloadRelease.Releasable {
    @Shadow
    private VoidBlossomEntity entity;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void coo$track(CallbackInfo ci) {
        ClientUnloadRelease.track(this);
    }

    @Override
    public void coo$release() {
        this.entity = null;
    }
}
