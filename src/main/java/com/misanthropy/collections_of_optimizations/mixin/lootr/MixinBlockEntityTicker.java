package com.misanthropy.collections_of_optimizations.mixin.lootr;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import noobanidus.mods.lootr.common.block.entity.BlockEntityTicker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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
        if (!CoOConfig.lootrSkipIdleBlockEntityTicker || !this.blockEntityEntries.isEmpty()) {
            return;
        }
        synchronized (this.pendingEntries) {
            if (this.pendingEntries.isEmpty()) {
                ci.cancel();
            }
        }
    }

    @WrapOperation(
            method = "onServerLevelTick",
            at = @At(value = "INVOKE", target = "Ljava/util/Collection;iterator()Ljava/util/Iterator;", ordinal = 0),
            require = 0
    )
    private static Iterator<?> coo$limitConversionCandidates(Collection<?> entries, Operation<Iterator<?>> original) {
        int budget = CoOConfig.lootrBlockEntityTickerBudget;
        Iterator<?> delegate = original.call(entries);
        if (budget <= 0 || entries.size() <= budget) {
            return delegate;
        }
        return new Iterator<>() {
            private int remaining = budget;

            @Override
            public boolean hasNext() {
                return this.remaining > 0 && delegate.hasNext();
            }

            @Override
            public Object next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.remaining--;
                return delegate.next();
            }

            @Override
            public void remove() {
                delegate.remove();
            }
        };
    }
}
