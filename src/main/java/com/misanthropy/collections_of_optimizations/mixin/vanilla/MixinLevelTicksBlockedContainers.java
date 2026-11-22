package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import net.minecraft.world.ticks.LevelTicks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = LevelTicks.class, remap = false)
public abstract class MixinLevelTicksBlockedContainers {

    // Reads the result instead of wrapping the call, so there is no per-call wrapper object or boxed long.
    @ModifyExpressionValue(
            method = "sortContainersToTick",
            at = @At(value = "INVOKE", target = "Ljava/util/function/LongPredicate;test(J)Z")
    )
    private boolean coo$deferBlockedContainer(boolean ticking, @Local Long2LongMap.Entry entry, @Local(argsOnly = true) long time) {
        if (!ticking && CoOConfig.vanillaDeferBlockedTickContainers) {
            entry.setValue(time + 20L);
        }
        return ticking;
    }
}
