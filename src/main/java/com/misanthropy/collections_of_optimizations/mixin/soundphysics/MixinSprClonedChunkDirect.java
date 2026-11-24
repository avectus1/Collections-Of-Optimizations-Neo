package com.misanthropy.collections_of_optimizations.mixin.soundphysics;

import com.sonicether.soundphysics.world.ClonedLevelChunk;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/** Same section reads as the original, without building a capturing lambda for every block a ray crosses. */
@Mixin(value = ClonedLevelChunk.class, remap = false)
public abstract class MixinSprClonedChunkDirect {
    /**
     * @author collections_of_optimizations
     * @reason no lambda per lookup
     */
    @Overwrite
    public BlockState getBlockState(@Nonnull BlockPos pos) {
        LevelChunkSection section = this.coo$section(pos.getY());
        return section != null && !section.hasOnlyAir()
                ? section.getBlockState(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15)
                : Blocks.AIR.defaultBlockState();
    }

    /**
     * @author collections_of_optimizations
     * @reason no lambda per lookup
     */
    @Overwrite
    public FluidState getFluidState(@Nonnull BlockPos pos) {
        LevelChunkSection section = this.coo$section(pos.getY());
        return section != null && !section.hasOnlyAir()
                ? section.getFluidState(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15)
                : Fluids.EMPTY.defaultFluidState();
    }

    private LevelChunkSection coo$section(int y) {
        ChunkAccess self = (ChunkAccess) (Object) this;
        int index = self.getSectionIndex(y);
        LevelChunkSection[] sections = self.getSections();
        return index >= 0 && index < sections.length ? sections[index] : null;
    }
}
