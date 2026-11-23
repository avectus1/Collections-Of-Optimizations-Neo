package com.misanthropy.collections_of_optimizations.mixin.perception;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import it.hurts.octostudios.perception.common.modules.trail.config.data.TrailConfigData;
import it.hurts.octostudios.perception.common.modules.trail.misc.wrapper.base.TrailWrapper;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Perception re-reads each trail's colors from text every time a trail is drawn, and looks for the nearest player
 * to push the trail away from them even when that push is 0 (the default). Remember parsed colors, and skip the
 * player search when it cannot change anything. Same trails.
 */
@Mixin(value = TrailWrapper.class, remap = false)
public abstract class MixinPerceptionTrailWrapper {
    @Unique
    private static final Map<String, Integer> coo$COLORS = new HashMap<>();

    @Shadow
    public abstract TrailConfigData getData();

    @Unique
    private static int coo$color(String text) {
        Integer cached = coo$COLORS.get(text);
        if (cached == null) {
            cached = (int) Long.parseLong(text.replace("#", ""), 16);
            if (coo$COLORS.size() > 256) {
                coo$COLORS.clear();
            }
            coo$COLORS.put(text, cached);
        }
        return cached;
    }

    @Inject(method = "getTrailFadeInColor", at = @At("HEAD"), cancellable = true)
    private void coo$fadeIn(CallbackInfoReturnable<Integer> cir) {
        if (CoOConfig.perceptionTrailColors) {
            cir.setReturnValue(coo$color(this.getData().getFadeInColor()));
        }
    }

    @Inject(method = "getTrailFadeOutColor", at = @At("HEAD"), cancellable = true)
    private void coo$fadeOut(CallbackInfoReturnable<Integer> cir) {
        if (CoOConfig.perceptionTrailColors) {
            cir.setReturnValue(coo$color(this.getData().getFadeOutColor()));
        }
    }

    @Redirect(method = "getTrailPosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getNearestPlayer(Lnet/minecraft/world/entity/Entity;D)Lnet/minecraft/world/entity/player/Player;"))
    private Player coo$noShiftNoSearch(Level level, Entity entity, double distance) {
        if (CoOConfig.perceptionTrailColors && this.getData().getBackwardShift() == 0.0F) {
            return null;
        }
        return level.getNearestPlayer(entity, distance);
    }
}
