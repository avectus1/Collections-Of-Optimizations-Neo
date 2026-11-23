package com.misanthropy.collections_of_optimizations.mixin.subtleeffects;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.SubtleEffectsRange;
import einstein.subtle_effects.ticking.tickers.entity.EntityTickerManager;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/** The inner range check right after the outer one only needs the distance (see SubtleEffectsRange). */
@Mixin(value = EntityTickerManager.class, remap = false)
public abstract class MixinSeEntityManagerRange {
    @Redirect(method = "updateTickersForEntity", at = @At(value = "INVOKE", target = "Leinstein/subtle_effects/ticking/tickers/entity/EntityTickerManager;isEntityInRange(Lnet/minecraft/world/entity/Entity;I)Z", ordinal = 1))
    private static boolean coo$inner(Entity entity, int range) {
        return CoOConfig.subtleeffectsRangeOnce ? SubtleEffectsRange.innerRange(entity, range) : EntityTickerManager.isEntityInRange(entity, range);
    }
}
