package com.misanthropy.collections_of_optimizations.mixin.alexsmobs;

import com.github.alexthe666.alexsmobs.entity.EntityRaccoon;
import com.github.alexthe666.alexsmobs.entity.ai.RaccoonAIBeg;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Raccoons look for the nearest player holding food on every tick they are idle. Checking every few ticks
 * makes them start begging a moment later at most. The tempt delay countdown still runs every tick.
 */
@Mixin(value = RaccoonAIBeg.class, remap = false)
public abstract class MixinRaccoonBegScan {
    @Shadow @Final protected EntityRaccoon raccoon;
    @Shadow private int delayTemptCounter;

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$scanLessOften(CallbackInfoReturnable<Boolean> cir) {
        int interval = CoOConfig.alexsmobsRaccoonBegInterval;
        if (interval > 1 && this.delayTemptCounter <= 0 && (this.raccoon.tickCount + this.raccoon.getId()) % interval != 0) {
            cir.setReturnValue(false);
        }
    }
}
