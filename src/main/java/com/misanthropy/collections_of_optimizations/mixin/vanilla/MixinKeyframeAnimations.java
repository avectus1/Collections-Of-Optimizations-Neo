package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ModelPartLookup;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mixin(value = KeyframeAnimations.class, remap = false)
public abstract class MixinKeyframeAnimations {

    @Inject(method = "animate", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$leanAnimate(HierarchicalModel<?> model, AnimationDefinition definition, long accumulatedTime,
                                        float scale, Vector3f target, CallbackInfo ci) {
        if (!CoOConfig.vanillaLeanKeyframeAnimation) {
            return;
        }
        ci.cancel();

        float seconds = (float) accumulatedTime / 1000.0F;
        if (definition.looping()) {
            seconds %= definition.lengthInSeconds();
        }

        for (Map.Entry<String, List<AnimationChannel>> entry : definition.boneAnimations().entrySet()) {
            Optional<ModelPart> found = CoOConfig.vanillaCacheModelPartLookups
                    ? ((ModelPartLookup) model).coo$cachedDescendant(entry.getKey())
                    : model.getAnyDescendantWithName(entry.getKey());
            if (found.isEmpty()) {
                continue;
            }

            ModelPart bone = found.get();
            List<AnimationChannel> channels = entry.getValue();
            for (int i = 0, channelCount = channels.size(); i < channelCount; i++) {
                AnimationChannel channel = channels.get(i);
                Keyframe[] keyframes = channel.keyframes();

                int low = 0;
                int span = keyframes.length;
                while (span > 0) {
                    int half = span / 2;
                    int probe = low + half;
                    if (seconds <= keyframes[probe].timestamp()) {
                        span = half;
                    } else {
                        low = probe + 1;
                        span -= half + 1;
                    }
                }

                int from = Math.max(0, low - 1);
                int to = Math.min(keyframes.length - 1, from + 1);
                Keyframe start = keyframes[from];
                Keyframe end = keyframes[to];
                float progress = to != from
                        ? Mth.clamp((seconds - start.timestamp()) / (end.timestamp() - start.timestamp()), 0.0F, 1.0F)
                        : 0.0F;

                end.interpolation().apply(target, progress, keyframes, from, to, scale);
                channel.target().apply(bone, target);
            }
        }
    }
}
