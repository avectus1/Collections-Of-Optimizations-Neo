package com.misanthropy.collections_of_optimizations.mixin.borninchaos;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * A Corpse Fish in water re-applies a fresh 40 tick Dolphin's Grace every single tick, which fires the
 * effect events and sends an effect update packet to every tracking player each time. Refreshing it
 * only once it has run down to half keeps it permanently applied exactly as before.
 */
@Mixin(targets = "net.mcreator.borninchaosv.procedures.CorpseFishPriObnovlieniiTikaSushchnostiProcedure", remap = false)
public abstract class MixinBicCorpseFishEffects {
    @WrapOperation(
            method = "execute",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z"),
            require = 0)
    private static boolean coo$skipFreshEffect(LivingEntity entity, MobEffectInstance effect, Operation<Boolean> original) {
        if (CoOConfig.borninchaosCorpseFishEffectRefresh) {
            MobEffectInstance current = entity.getEffect(effect.getEffect());
            if (current != null && current.getAmplifier() == effect.getAmplifier()
                    && current.getDuration() > effect.getDuration() / 2) {
                return false;
            }
        }
        return original.call(entity, effect);
    }
}
