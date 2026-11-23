package com.misanthropy.collections_of_optimizations.mixin.notenoughanimations;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.NeaFreezeMemo;
import dev.tr7zw.notenoughanimations.access.PlayerData;
import dev.tr7zw.notenoughanimations.animations.fullbody.FreezingAnimation;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;

/**
 * NEA checks every frame, for every visible player, whether they stand in powder snow: an armour scan plus
 * a scan of every block state in the player's box. Positions and blocks only move on game ticks, so answer
 * once per player per tick.
 */
@Mixin(value = FreezingAnimation.class, remap = false)
public abstract class MixinFreezingAnimationTickMemo {
    @WrapMethod(method = "isValid")
    private boolean coo$oncePerTick(AbstractClientPlayer entity, PlayerData data, Operation<Boolean> original) {
        if (!CoOConfig.notenoughanimationsFreezeCheckPerTick) {
            return original.call(entity, data);
        }
        NeaFreezeMemo memo = (NeaFreezeMemo) entity;
        int tick = entity.tickCount;
        if (memo.coo$freezeTick() == tick) {
            return memo.coo$freezeValue() == 1;
        }
        boolean value = original.call(entity, data);
        memo.coo$setFreeze(tick, (byte) (value ? 1 : 0));
        return value;
    }
}
