package com.misanthropy.collections_of_optimizations.mixin.undergarden;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import quek.undergarden.entity.monster.cavern.Muncher;

/**
 * Munchers check the mob griefing rule only when bumping into blocks vertically, so with mobGriefing off they still
 * chew through walls they bump into sideways. Check the rule for both.
 */
@Mixin(value = Muncher.class, remap = false)
public abstract class MixinUgMuncherGriefing {
    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;destroyBlock(Lnet/minecraft/core/BlockPos;ZLnet/minecraft/world/entity/Entity;)Z"))
    private boolean coo$respectRule(Level level, net.minecraft.core.BlockPos pos, boolean drop, Entity breaker) {
        if (CoOConfig.undergardenMuncherGriefing && ((Entity) (Object) this).horizontalCollision && !EventHooks.canEntityGrief(level, breaker)) {
            return false;
        }
        return level.destroyBlock(pos, drop, breaker);
    }
}
