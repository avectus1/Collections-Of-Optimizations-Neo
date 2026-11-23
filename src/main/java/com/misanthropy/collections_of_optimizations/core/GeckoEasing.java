package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
import java.util.concurrent.ConcurrentHashMap;
import software.bernie.geckolib.animation.EasingType;

/**
 * GeckoLib rebuilds an easing curve (a small chain of lambdas) for every bone, axis and frame, and runs it through
 * boxed Double calls. GeckoLib's own built-in curves never change, so build each one once, and call GeckoLib's own
 * lambdas with plain numbers. Curves from other mods, and curves with an argument, are left exactly as they were.
 */
public final class GeckoEasing {
    private static final ClassValue<Boolean> OWN = new ClassValue<>() {
        @Override
        protected Boolean computeValue(Class<?> type) {
            return type.isHidden() && type.getNestHost() == EasingType.class;
        }
    };
    private static final ConcurrentHashMap<EasingType, Double2DoubleFunction> PLAIN = new ConcurrentHashMap<>();

    private GeckoEasing() {
    }

    public static Double2DoubleFunction transformer(EasingType type, Double value) {
        if (value != null || !CoOConfig.geckolibEasing || !OWN.get(type.getClass())) {
            return type.buildTransformer(value);
        }
        Double2DoubleFunction cached = PLAIN.get(type);
        if (cached == null) {
            cached = type.buildTransformer(null);
            PLAIN.putIfAbsent(type, cached);
        }
        return cached;
    }

    public static double apply(Double2DoubleFunction function, double value) {
        return CoOConfig.geckolibEasing && OWN.get(function.getClass()) ? function.get(value) : function.apply(value);
    }
}
