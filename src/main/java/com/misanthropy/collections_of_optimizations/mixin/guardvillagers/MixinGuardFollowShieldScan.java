package com.misanthropy.collections_of_optimizations.mixin.guardvillagers;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tallestegg.guardvillagers.common.entities.Guard;

/**
 * Every guard searches the area for shield guards to follow on every tick it is not already following one.
 * Looking twice a second instead finds the same guards a few ticks later.
 */
@Mixin(targets = "tallestegg.guardvillagers.common.entities.Guard$FollowShieldGuards", remap = false)
public abstract class MixinGuardFollowShieldScan {
    @Shadow @Final private Guard taskOwner;

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$scanLessOften(CallbackInfoReturnable<Boolean> cir) {
        int interval = CoOConfig.guardvillagersShieldFollowInterval;
        if (interval > 1 && (this.taskOwner.tickCount + this.taskOwner.getId()) % interval != 0) {
            cir.setReturnValue(false);
        }
    }
}
