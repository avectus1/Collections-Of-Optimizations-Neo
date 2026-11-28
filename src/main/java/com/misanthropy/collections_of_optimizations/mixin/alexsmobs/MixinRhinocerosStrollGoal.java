package com.misanthropy.collections_of_optimizations.mixin.alexsmobs;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BooleanSupplier;

/**
 * A rhino's village stroll goal runs the full vanilla check first, which near a village searches a Citadel path on
 * the server thread every time the goal is tried, and only then asks whether the rhino trusts anyone. Wild rhinos
 * never trust anyone, so they paid for a path they could never use. Ask the cheap question first.
 */
@Mixin(targets = "com.github.alexthe666.alexsmobs.entity.EntityRhinoceros$StrollGoal", remap = false)
public abstract class MixinRhinocerosStrollGoal extends MoveThroughVillageGoal {
    private MixinRhinocerosStrollGoal(PathfinderMob mob, double speed, boolean onlyAtNight, int distance, BooleanSupplier canDealWithDoors) {
        super(mob, speed, onlyAtNight, distance, canDealWithDoors);
    }

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$skipUntrusted(CallbackInfoReturnable<Boolean> cir) {
        if (CoOConfig.alexsmobsRhinoStrollTrustFirst && this.mob instanceof RhinocerosTrustAccessor rhino && rhino.coo$getTrustedUUIDs().isEmpty()) {
            cir.setReturnValue(false);
        }
    }
}
