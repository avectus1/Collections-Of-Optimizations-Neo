package com.misanthropy.collections_of_optimizations.mixin.undergarden;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import quek.undergarden.entity.monster.rotspawn.RotspawnMonster;

/**
 * Every 2 seconds each Rotspawn checks every block in a 33x9x33 box (almost 10,000 blocks) for something that scares
 * it. Each chunk section keeps a short list of the block kinds in it, so first check those lists: when no section in
 * the box can contain a repellent block (nearly always), the full scan would find nothing, so skip it.
 */
@Mixin(value = RotspawnMonster.class, remap = false)
public abstract class MixinUgRotspawnRepelScan {
    @Unique
    private static final Predicate<net.minecraft.world.level.block.state.BlockState> coo$REPELLENT = s -> s.is(BlockTags.HOGLIN_REPELLENTS);

    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;findClosestMatch(Lnet/minecraft/core/BlockPos;IILjava/util/function/Predicate;)Ljava/util/Optional;"))
    private Optional<BlockPos> coo$paletteFirst(BlockPos center, int horizontal, int vertical, Predicate<BlockPos> filter) {
        if (CoOConfig.undergardenRepelPaletteCheck && !coo$mayContain(((Entity) (Object) this).level(), center, horizontal, vertical)) {
            return Optional.empty();
        }
        return BlockPos.findClosestMatch(center, horizontal, vertical, filter);
    }

    @Unique
    private static boolean coo$mayContain(Level level, BlockPos center, int horizontal, int vertical) {
        int minY = Math.max(center.getY() - vertical, level.getMinBuildHeight());
        int maxY = Math.min(center.getY() + vertical, level.getMaxBuildHeight() - 1);
        if (minY > maxY) {
            return false;
        }
        int minCX = SectionPos.blockToSectionCoord(center.getX() - horizontal);
        int maxCX = SectionPos.blockToSectionCoord(center.getX() + horizontal);
        int minCZ = SectionPos.blockToSectionCoord(center.getZ() - horizontal);
        int maxCZ = SectionPos.blockToSectionCoord(center.getZ() + horizontal);
        for (int cx = minCX; cx <= maxCX; cx++) {
            for (int cz = minCZ; cz <= maxCZ; cz++) {
                LevelChunk chunk = level.getChunkSource().getChunkNow(cx, cz);
                if (chunk == null) {
                    return true;
                }
                LevelChunkSection[] sections = chunk.getSections();
                int from = chunk.getSectionIndex(minY);
                int to = chunk.getSectionIndex(maxY);
                for (int i = from; i <= to; i++) {
                    if (i >= 0 && i < sections.length && !sections[i].hasOnlyAir() && sections[i].maybeHas(coo$REPELLENT)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
