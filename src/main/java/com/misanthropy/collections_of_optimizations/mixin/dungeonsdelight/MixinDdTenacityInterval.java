package com.misanthropy.collections_of_optimizations.mixin.dungeonsdelight;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "net.yirmiri.dungeonsdelight.common.effect.TenacityEffect", remap = false)
public abstract class MixinDdTenacityInterval {

    @Inject(method = "isDurationEffectTick", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$tickEveryTick(int duration, int amplifier, CallbackInfoReturnable<Boolean> cir) {
        if (CoOConfig.dungeonsdelightPerPlayerTenacityInterval) {
            cir.setReturnValue(true);
        }
    }

    @WrapWithCondition(
            method = "applyEffectTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;heal(F)V"),
            require = 0
    )
    private boolean coo$healOnOwnInterval(Player player, float amount, @Local(argsOnly = true) int amplifier) {
        return !CoOConfig.dungeonsdelightPerPlayerTenacityInterval || coo$due(player, amplifier);
    }

    @WrapWithCondition(
            method = "applyEffectTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;tick(Lnet/minecraft/world/entity/player/Player;)V"),
            require = 0
    )
    private boolean coo$foodTickOnOwnInterval(FoodData food, Player player, @Local(argsOnly = true) int amplifier) {
        return !CoOConfig.dungeonsdelightPerPlayerTenacityInterval || coo$due(player, amplifier);
    }

    @Unique
    private boolean coo$due(Player player, int amplifier) {
        Holder<MobEffect> self = BuiltInRegistries.MOB_EFFECT.wrapAsHolder((MobEffect) (Object) this);
        MobEffectInstance instance = player.getEffect(self);
        if (instance == null) {
            return true;
        }
        int food = player.getFoodData().getFoodLevel();
        int step = (food != 0 ? food * 3 : 1) - amplifier * 2;
        return step == 0 || instance.getDuration() % step == 0;
    }
}
