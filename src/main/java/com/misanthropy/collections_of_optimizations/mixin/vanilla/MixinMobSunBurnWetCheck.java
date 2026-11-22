package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.VanillaOverrideChecks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Undead mobs check "am I wet" every tick before the random roll that decides whether they burn at all.
 * The wet check is the expensive part, so only do it once the roll has passed.
 * Uses plain redirects and an instance flag so the per-tick check creates no objects.
 */
@Mixin(value = Mob.class, remap = false)
public abstract class MixinMobSunBurnWetCheck {

    @Unique
    private boolean coo$wetDeferred;

    @Redirect(
            method = "isSunBurnTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;isInWaterRainOrBubble()Z"),
            require = 0
    )
    private boolean coo$deferWetCheck(Mob mob) {
        if (CoOConfig.vanillaDeferSunBurnWetCheck && VanillaOverrideChecks.stockWetCheck(mob.getClass())) {
            this.coo$wetDeferred = true;
            return false;
        }
        this.coo$wetDeferred = false;
        return mob.isInWaterRainOrBubble();
    }

    @Redirect(
            method = "isSunBurnTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;canSeeSky(Lnet/minecraft/core/BlockPos;)Z"),
            require = 0
    )
    private boolean coo$wetBeforeSky(Level level, BlockPos pos) {
        boolean deferred = this.coo$wetDeferred;
        this.coo$wetDeferred = false;
        if (deferred && ((Mob) (Object) this).isInWaterRainOrBubble()) {
            return false;
        }
        return level.canSeeSky(pos);
    }
}
