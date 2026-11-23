package com.misanthropy.collections_of_optimizations.mixin.geckolib;

import com.misanthropy.collections_of_optimizations.core.GeckoEasing;
import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import software.bernie.geckolib.animation.EasingType;
import software.bernie.geckolib.animation.keyframe.AnimationPoint;

/** See GeckoEasing. Same math, same results. */
@Mixin(value = EasingType.class, remap = false)
public interface MixinGeckoEasingType {
    /**
     * @author avectus1
     * @reason reuse GeckoLib's built-in curves and skip boxing (see GeckoEasing)
     */
    @Overwrite(remap = false)
    default double apply(AnimationPoint animationPoint, Double easingValue, double lerpValue) {
        if (animationPoint.currentTick() >= animationPoint.transitionLength()) {
            return (double) (float) animationPoint.animationEndValue();
        }
        Double2DoubleFunction transformer = GeckoEasing.transformer((EasingType) this, easingValue);
        return Mth.lerp(GeckoEasing.apply(transformer, lerpValue), animationPoint.animationStartValue(), animationPoint.animationEndValue());
    }

    /**
     * @author avectus1
     * @reason skip boxing for GeckoLib's own curves
     */
    @Overwrite(remap = false)
    static Double2DoubleFunction easeOut(Double2DoubleFunction function) {
        return time -> 1.0 - GeckoEasing.apply(function, 1.0 - time);
    }

    /**
     * @author avectus1
     * @reason skip boxing for GeckoLib's own curves
     */
    @Overwrite(remap = false)
    static Double2DoubleFunction easeInOut(Double2DoubleFunction function) {
        return time -> time < 0.5 ? GeckoEasing.apply(function, time * 2.0) / 2.0 : 1.0 - GeckoEasing.apply(function, (1.0 - time) * 2.0) / 2.0;
    }
}
