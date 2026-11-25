package com.misanthropy.collections_of_optimizations.mixin.lootr;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import noobanidus.mods.lootr.common.block.entity.BlockEntityTicker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

@Mixin(value = BlockEntityTicker.class, remap = false)
public abstract class MixinBlockEntityTicker {
    @Shadow
    @Final
    private Map<?, ?> blockEntityEntries;

    @Shadow
    @Final
    private Map<?, ?> pendingEntries;

    @Inject(method = "onServerLevelTick", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$skipIdlePass(CallbackInfo ci) {
        if (!CoOConfig.lootrSkipIdleTileTicker || !this.blockEntityEntries.isEmpty()) {
            return;
        }
        synchronized (this.pendingEntries) {
            if (this.pendingEntries.isEmpty()) {
                ci.cancel();
            }
        }
    }

    /** Where the previous capped pass stopped, so every waiting chunk gets its turn. */
    @Unique
    private final int[] coo$cursor = new int[1];

    @WrapOperation(
            method = "onServerLevelTick",
            at = @At(value = "INVOKE", target = "Ljava/util/Collection;iterator()Ljava/util/Iterator;", ordinal = 0),
            require = 0
    )
    private Iterator<?> coo$limitConversionCandidates(Collection<?> entries, Operation<Iterator<?>> original) {
        int budget = CoOConfig.lootrTileTickerBudget;
        Iterator<?> delegate = original.call(entries);
        int size = entries.size();
        if (budget <= 0 || size <= budget) {
            this.coo$cursor[0] = 0;
            return delegate;
        }
        // Lootr keeps chunks waiting for their neighbours to load in this map, sometimes for as long as the
        // player stays nearby. Always reading the same first entries would leave every later chunk (a freshly
        // generated village, say) unconverted until someone opens its chests, so resume after the last pass.
        int[] cursor = this.coo$cursor;
        int start = cursor[0] < size ? cursor[0] : 0;
        for (int i = 0; i < start && delegate.hasNext(); i++) {
            delegate.next();
        }
        cursor[0] = start;
        return new Iterator<>() {
            private int remaining = budget;

            @Override
            public boolean hasNext() {
                if (this.remaining <= 0) {
                    return false;
                }
                if (!delegate.hasNext()) {
                    cursor[0] = 0;
                    return false;
                }
                return true;
            }

            @Override
            public Object next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.remaining--;
                cursor[0]++;
                return delegate.next();
            }

            @Override
            public void remove() {
                delegate.remove();
                cursor[0]--;
            }
        };
    }
}
