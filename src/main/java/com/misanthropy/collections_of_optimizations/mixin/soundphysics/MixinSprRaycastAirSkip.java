package com.misanthropy.collections_of_optimizations.mixin.soundphysics;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.sonicether.soundphysics.utils.RaycastUtils;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Every sound fires dozens of 256-block rays, and almost every block they cross is air. For air the original
 * still looks the block up a second time for its fluid and clips two empty shapes; skip straight past it.
 * Other blocks take their fluid from the block state, which is what the level lookup returns anyway.
 */
@Mixin(value = RaycastUtils.class, remap = false)
public abstract class MixinSprRaycastAirSkip {
    /**
     * @author collections_of_optimizations
     * @reason skip air blocks and the second lookup per block
     */
    @Overwrite
    public static BlockHitResult rayCast(@Nullable BlockGetter blockGetter, Vec3 from, Vec3 to, @Nullable BlockPos ignore) {
        if (blockGetter == null) {
            return BlockHitResult.miss(to, Direction.getNearest(from.subtract(to)), BlockPos.containing(to));
        }
        final boolean fast = CoOConfig.soundphysicsFastRaycast;
        return BlockGetter.traverseBlocks(from, to, blockGetter, (g, pos) -> {
            if (pos.equals(ignore)) {
                return null;
            }
            BlockState blockState = g.getBlockState(pos);
            if (fast && blockState.isAir()) {
                return null;
            }
            FluidState fluidState = fast ? blockState.getFluidState() : g.getFluidState(pos);
            VoxelShape shape = ClipContext.Block.COLLIDER.get(blockState, g, pos, CollisionContext.empty());
            BlockHitResult blockHit = g.clipWithInteractionOverride(from, to, pos, shape, blockState);
            BlockHitResult fluidHit = fluidState.isEmpty() ? null : fluidState.getShape(g, pos).clip(from, to, pos);
            if (fluidHit == null) {
                return blockHit;
            }
            if (blockHit == null) {
                return fluidHit;
            }
            return from.distanceToSqr(blockHit.getLocation()) <= from.distanceToSqr(fluidHit.getLocation()) ? blockHit : fluidHit;
        }, g -> BlockHitResult.miss(to, Direction.getNearest(from.subtract(to)), BlockPos.containing(to)));
    }
}
