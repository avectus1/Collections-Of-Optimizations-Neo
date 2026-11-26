package com.misanthropy.collections_of_optimizations.mixin.obscuretooltips;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.TooltipIconGuard;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.lodestar.lodestone.handlers.screenparticle.ScreenParticleHandler;

/**
 * Obscure Tooltips draws the tooltip icon at (0, 0) and moves it into place with the pose matrix. For items drawn
 * at (0, 0), Lodestone takes the particle position from the pose's translation, then draws the particles with that
 * same pose, so the offset is applied twice. Glowing items (Malum spirits, Geas and so on) leave their purple
 * sparkles stuck far away from the tooltip, usually near the bottom right of the screen. Item particles are skipped
 * for items drawn inside an Obscure tooltip; items in slots keep them.
 */
@Mixin(value = ScreenParticleHandler.class, remap = false)
public abstract class MixinLodestoneTooltipIconParticles {
    @Inject(method = "renderItemStackEarly", at = @At("HEAD"), cancellable = true)
    private static void coo$skipInTooltip(PoseStack poseStack, ItemStack stack, int x, int y, CallbackInfo ci) {
        if (TooltipIconGuard.depth > 0 && CoOConfig.obscureTooltipsNoIconParticles) {
            ci.cancel();
        }
    }
}
