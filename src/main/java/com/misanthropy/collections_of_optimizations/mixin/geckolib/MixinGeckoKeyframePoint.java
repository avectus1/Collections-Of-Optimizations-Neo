package com.misanthropy.collections_of_optimizations.mixin.geckolib;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import java.util.List;
import java.util.RandomAccess;
import net.minecraft.core.Direction.Axis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.keyframe.AnimationPoint;
import software.bernie.geckolib.animation.keyframe.Keyframe;
import software.bernie.geckolib.animation.keyframe.KeyframeLocation;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.Constant;

/**
 * For every animated bone and axis, GeckoLib finds the current keyframe by walking the list with an iterator and
 * wrapping the result in a throwaway object. Walk it by index and skip the wrapper. Same keyframe, same values.
 */
@Mixin(value = AnimationController.class, remap = false)
public abstract class MixinGeckoKeyframePoint {
    @Shadow
    private KeyframeLocation<Keyframe<MathValue>> getCurrentKeyFrameLocation(List<Keyframe<MathValue>> frames, double ageInTicks) {
        throw new AssertionError();
    }

    /**
     * @author avectus1
     * @reason find the keyframe without an iterator or wrapper object
     */
    @Overwrite
    private AnimationPoint getAnimationPointAtTick(List<Keyframe<MathValue>> frames, double tick, boolean isRotation, Axis axis) {
        Keyframe<MathValue> frame = null;
        double startTick = tick;
        if (CoOConfig.geckolibKeyframeLookup && frames instanceof RandomAccess) {
            double total = 0.0;
            for (int i = 0, size = frames.size(); i < size; i++) {
                Keyframe<MathValue> candidate = frames.get(i);
                total += candidate.length();
                if (total > tick) {
                    frame = candidate;
                    startTick = tick - (total - candidate.length());
                    break;
                }
            }
            if (frame == null) {
                frame = frames.getLast();
            }
        } else {
            KeyframeLocation<Keyframe<MathValue>> location = this.getCurrentKeyFrameLocation(frames, tick);
            frame = location.keyframe();
            startTick = location.startTick();
        }
        double startValue = frame.startValue().get();
        double endValue = frame.endValue().get();
        if (isRotation) {
            if (!(frame.startValue() instanceof Constant)) {
                startValue = Math.toRadians(startValue);
                if (axis == Axis.X || axis == Axis.Y) {
                    startValue *= -1.0;
                }
            }
            if (!(frame.endValue() instanceof Constant)) {
                endValue = Math.toRadians(endValue);
                if (axis == Axis.X || axis == Axis.Y) {
                    endValue *= -1.0;
                }
            }
        }
        return new AnimationPoint(frame, startTick, frame.length(), startValue, endValue);
    }
}
