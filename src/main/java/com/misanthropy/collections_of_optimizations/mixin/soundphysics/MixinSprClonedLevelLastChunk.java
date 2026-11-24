package com.misanthropy.collections_of_optimizations.mixin.soundphysics;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.sonicether.soundphysics.world.ClonedClientLevel;
import com.sonicether.soundphysics.world.ClonedLevelChunk;
import java.util.HashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/**
 * Rays walk block by block and stay in one chunk for many steps, but every step built a new ChunkPos and
 * hashed it into the chunk map. Remember the last chunk (in one immutable holder, so the sound thread and
 * the main thread can't see a half-updated pair).
 */
@Mixin(value = ClonedClientLevel.class, remap = false)
public abstract class MixinSprClonedLevelLastChunk {
    @Shadow
    @Final
    private HashMap<ChunkPos, ClonedLevelChunk> clonedLevelChunks;

    @Unique
    private static final Object coo$MISSING = new Object();

    @Unique
    private Object[] coo$last;

    @Unique
    private ClonedLevelChunk coo$chunkAt(BlockPos pos) {
        int cx = pos.getX() >> 4;
        int cz = pos.getZ() >> 4;
        if (!CoOConfig.soundphysicsLastChunk) {
            return this.clonedLevelChunks.get(new ChunkPos(cx, cz));
        }
        long key = ChunkPos.asLong(cx, cz);
        Object[] last = this.coo$last;
        if (last != null && (Long) last[0] == key) {
            return last[1] == coo$MISSING ? null : (ClonedLevelChunk) last[1];
        }
        ClonedLevelChunk chunk = this.clonedLevelChunks.get(new ChunkPos(cx, cz));
        this.coo$last = new Object[]{key, chunk == null ? coo$MISSING : chunk};
        return chunk;
    }

    /**
     * @author collections_of_optimizations
     * @reason reuse the last chunk lookup
     */
    @Overwrite
    public BlockState getBlockState(BlockPos pos) {
        if (((ClonedClientLevel) (Object) this).isOutsideBuildHeight(pos)) {
            return Blocks.VOID_AIR.defaultBlockState();
        }
        ClonedLevelChunk chunk = this.coo$chunkAt(pos);
        return chunk == null ? Blocks.VOID_AIR.defaultBlockState() : chunk.getBlockState(pos);
    }

    /**
     * @author collections_of_optimizations
     * @reason reuse the last chunk lookup
     */
    @Overwrite
    public FluidState getFluidState(BlockPos pos) {
        if (((ClonedClientLevel) (Object) this).isOutsideBuildHeight(pos)) {
            return Fluids.EMPTY.defaultFluidState();
        }
        ClonedLevelChunk chunk = this.coo$chunkAt(pos);
        return chunk == null ? Fluids.EMPTY.defaultFluidState() : chunk.getFluidState(pos);
    }
}
