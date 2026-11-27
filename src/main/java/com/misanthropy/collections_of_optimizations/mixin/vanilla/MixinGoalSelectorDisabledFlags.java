package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import org.spongepowered.asm.mixin.injection.Redirect;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = GoalSelector.class, remap = false)
public abstract class MixinGoalSelectorDisabledFlags {

    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;goalContainsAnyFlags(Lnet/minecraft/world/entity/ai/goal/WrappedGoal;Ljava/util/EnumSet;)Z"
            ),
            require = 0
    )
    private boolean coo$skipWhenNothingDisabled(WrappedGoal goal, EnumSet<Goal.Flag> disabled) {
        if (CoOConfig.vanillaSkipEmptyDisabledFlags && disabled.isEmpty()) {
            return false;
        }
        for (Goal.Flag flag : goal.getFlags()) {
            if (disabled.contains(flag)) {
                return true;
            }
        }
        return false;
    }
}
