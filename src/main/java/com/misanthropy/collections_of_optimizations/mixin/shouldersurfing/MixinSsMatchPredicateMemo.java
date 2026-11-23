package com.misanthropy.collections_of_optimizations.mixin.shouldersurfing;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.github.exopandora.shouldersurfing.api.util.Util;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Shoulder Surfing's adaptive crosshair compiles every item pattern from its config into a regular expression again
 * each time it checks the held items (several times per frame). Compile each pattern once.
 */
@Mixin(value = Util.class, remap = false)
public abstract class MixinSsMatchPredicateMemo {
    @Unique
    private static final ConcurrentHashMap<String, Predicate<String>> coo$PATTERNS = new ConcurrentHashMap<>();

    @Inject(method = "expressionToMatchPredicate", at = @At("HEAD"), cancellable = true)
    private static void coo$cached(String expression, CallbackInfoReturnable<Predicate<String>> cir) {
        if (!CoOConfig.shouldersurfingPatternMemo || expression == null) {
            return;
        }
        Predicate<String> cached = coo$PATTERNS.get(expression);
        if (cached != null) {
            cir.setReturnValue(cached);
        }
    }

    @Inject(method = "expressionToMatchPredicate", at = @At("RETURN"))
    private static void coo$remember(String expression, CallbackInfoReturnable<Predicate<String>> cir) {
        if (CoOConfig.shouldersurfingPatternMemo && expression != null && coo$PATTERNS.size() < 1024) {
            coo$PATTERNS.putIfAbsent(expression, cir.getReturnValue());
        }
    }
}
