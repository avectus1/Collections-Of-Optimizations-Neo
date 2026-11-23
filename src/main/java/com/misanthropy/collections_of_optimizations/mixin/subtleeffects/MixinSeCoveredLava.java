package com.misanthropy.collections_of_optimizations.mixin.subtleeffects;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.bawnorton.mixinsquared.TargetHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.LavaFluid;
import org.spongepowered.asm.mixin.Mixin;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Subtle Effects' lava sparks check up to 9 neighbours of every lava block the client animates, then give up when
 * the lava is covered from above (which is nearly all lava in a Nether lava sea). Check the cover first.
 */
@Mixin(value = LavaFluid.class, priority = 1500, remap = false)
public abstract class MixinSeCoveredLava {
    @TargetHandler(mixin = "einstein.subtle_effects.mixin.client.fluid.LavaFluidMixin", name = "animateTick")
    @WrapMethod(method = "@MixinSquared:Handler", require = 0)
    private void coo$skipCovered(Level level, BlockPos pos, FluidState state, RandomSource random, CallbackInfo ci, Operation<Void> original) {
        if (CoOConfig.subtleeffectsCoveredLava
                && !(state.hasProperty(FlowingFluid.FALLING) && state.getValue(FlowingFluid.FALLING))
                && ((Fluid) (Object) this).isSame(level.getFluidState(pos).getType())
                && !level.getBlockState(pos).isSolidRender(level, pos)) {
            BlockPos above = pos.above();
            if (level.getBlockState(above).isSolidRender(level, above) || !level.getFluidState(above).isEmpty()) {
                return;
            }
        }
        original.call(level, pos, state, random, ci);
    }
}
