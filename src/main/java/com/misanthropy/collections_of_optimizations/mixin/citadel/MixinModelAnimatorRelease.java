package com.misanthropy.collections_of_optimizations.mixin.citadel;

import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.citadel.client.model.ModelAnimator;
import com.misanthropy.collections_of_optimizations.core.ClientUnloadRelease;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Every Citadel model (all of Alex's Mobs and Alex's Caves) keeps the last mob it animated. Models live for
 * the whole game, so after leaving a world or changing dimension each model still holds a mob from the old
 * world, and through it that whole client world. Let those references go when a client world unloads.
 */
@Mixin(value = ModelAnimator.class, remap = false)
public abstract class MixinModelAnimatorRelease implements ClientUnloadRelease.Releasable {
    @Shadow
    private IAnimatedEntity entity;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void coo$track(CallbackInfo ci) {
        ClientUnloadRelease.track(this);
    }

    @Override
    public void coo$release() {
        this.entity = null;
    }
}
