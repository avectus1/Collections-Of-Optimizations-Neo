package com.misanthropy.collections_of_optimizations.mixin.subtleeffects;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import einstein.subtle_effects.ticking.tickers.Ticker;
import einstein.subtle_effects.ticking.tickers.TickerManager;
import java.util.List;
import java.util.function.Consumer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Subtle Effects removes finished effect tickers one by one from a big list, searching the whole list for each one.
 * With many mobs around, hundreds can finish in the same tick. Remove all finished tickers in one pass instead.
 */
@Mixin(value = TickerManager.class, remap = false)
public abstract class MixinSeTickerRemoval {
    @Shadow
    @Final
    private static List<Ticker> TICKERS;

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V", ordinal = 1))
    private static void coo$removeInOnePass(List<Ticker> removeQueue, Consumer<Ticker> action) {
        if (!CoOConfig.subtleeffectsTickerRemoval || removeQueue.size() < 4) {
            removeQueue.forEach(action);
            return;
        }
        TICKERS.removeIf(Ticker::isRemoved);
    }
}
