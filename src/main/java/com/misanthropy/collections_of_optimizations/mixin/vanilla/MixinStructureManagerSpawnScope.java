package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.core.SpawnScope;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/** Structure lookups made while a spawn attempt is running reuse that attempt's already loaded chunks. */
@Mixin(value = StructureManager.class, remap = false)
public abstract class MixinStructureManagerSpawnScope {

    @Redirect(method = {"getAllStructuresAt", "hasAnyStructureAt", "fillStartsForStructure",
            "startsForStructure(Lnet/minecraft/core/SectionPos;Lnet/minecraft/world/level/levelgen/structure/Structure;)Ljava/util/List;"},
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelAccessor;getChunk(IILnet/minecraft/world/level/chunk/status/ChunkStatus;)Lnet/minecraft/world/level/chunk/ChunkAccess;"))
    private ChunkAccess coo$scopedChunk(LevelAccessor level, int x, int z, ChunkStatus status) {
        return SpawnScope.active(level) ? SpawnScope.chunk(level, x, z, status) : level.getChunk(x, z, status);
    }
}
