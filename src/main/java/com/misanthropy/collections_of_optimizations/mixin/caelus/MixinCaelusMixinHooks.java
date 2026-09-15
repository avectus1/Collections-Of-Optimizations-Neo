package com.misanthropy.collections_of_optimizations.mixin.caelus;

import com.illusivesoulworks.caelus.mixin.util.MixinHooks;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MixinHooks.class, remap = false)
public abstract class MixinCaelusMixinHooks {
    @Inject(method = "canFly", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$skipGroundedNonPlayers(LivingEntity entity, boolean oldFlag, boolean newFlag,
            CallbackInfoReturnable<Boolean> cir) {
        if (CoOConfig.caelusSkipGroundedNonPlayers && !oldFlag && !newFlag && !(entity instanceof Player)) {
            cir.setReturnValue(false);
        }
    }
}
