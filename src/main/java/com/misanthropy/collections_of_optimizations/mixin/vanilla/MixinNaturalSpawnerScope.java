package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.SpawnScope;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = NaturalSpawner.class, remap = false)
public abstract class MixinNaturalSpawnerScope {

    /**
     * Opened around the call rather than with a method wrapper, so that no wrapper object is allocated for
     * each spawn attempt (thousands per tick); try/finally still closes the scope if the attempt throws.
     */
    @Redirect(method = {"spawnCategoryForChunk",
            "spawnCategoryForPosition(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;)V"},
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/NaturalSpawner;spawnCategoryForPosition(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V"))
    private static void coo$scope(MobCategory category, ServerLevel level, ChunkAccess chunk, BlockPos pos,
                                  NaturalSpawner.SpawnPredicate filter, NaturalSpawner.AfterSpawnCallback callback) {
        if (!CoOConfig.vanillaSpawnChunkScope || !SpawnScope.enter(level, chunk)) {
            NaturalSpawner.spawnCategoryForPosition(category, level, chunk, pos, filter, callback);
            return;
        }
        try {
            NaturalSpawner.spawnCategoryForPosition(category, level, chunk, pos, filter, callback);
        } finally {
            SpawnScope.exit();
        }
    }

    @Redirect(method = "getRandomSpawnMobAt",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getBiome(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/Holder;"))
    private static Holder<Biome> coo$spawnBiome(ServerLevel level, BlockPos pos) {
        return SpawnScope.active(level) ? SpawnScope.biome(level, pos) : level.getBiome(pos);
    }

    @Redirect(method = "mobsAt",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getBiome(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/Holder;"))
    private static Holder<Biome> coo$mobsBiome(ServerLevel level, BlockPos pos) {
        return SpawnScope.active(level) ? SpawnScope.biome(level, pos) : level.getBiome(pos);
    }

    @Redirect(method = "isRightDistanceToPlayerAndSpawnPoint",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;isNaturalSpawningAllowed(Lnet/minecraft/core/BlockPos;)Z"))
    private static boolean coo$spawningAllowed(ServerLevel level, BlockPos pos) {
        return SpawnScope.active(level) ? SpawnScope.spawningAllowed(level, pos) : level.isNaturalSpawningAllowed(pos);
    }

    /**
     * The first attempt of a pack picks a mob from the spawn list at a spot, then straight away checks
     * that the same mob is in the spawn list at the same spot, which builds the list (and fires the
     * potential-spawns event) a second time. Hand back the list built a moment ago instead.
     */
    @Redirect(method = "canSpawnMobAt",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/NaturalSpawner;mobsAt(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Holder;)Lnet/minecraft/util/random/WeightedRandomList;"))
    private static WeightedRandomList<MobSpawnSettings.SpawnerData> coo$reuseMobs(ServerLevel level, StructureManager structures, ChunkGenerator generator,
                                                                               MobCategory category, BlockPos pos, Holder<Biome> biome) {
        if (SpawnScope.active(level)) {
            WeightedRandomList<MobSpawnSettings.SpawnerData> last = SpawnScope.lastMobs(category, pos);
            if (last != null) {
                if (SpawnScope.VERIFY) {
                    SpawnScope.verify(last.unwrap().equals(mobsAt(level, structures, generator, category, pos, biome).unwrap()), "mobs");
                }
                return last;
            }
        }
        return mobsAt(level, structures, generator, category, pos, biome);
    }

    @Redirect(method = "getRandomSpawnMobAt",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/NaturalSpawner;mobsAt(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Holder;)Lnet/minecraft/util/random/WeightedRandomList;"))
    private static WeightedRandomList<MobSpawnSettings.SpawnerData> coo$keepMobs(ServerLevel level, StructureManager structures, ChunkGenerator generator,
                                                                              MobCategory category, BlockPos pos, Holder<Biome> biome) {
        WeightedRandomList<MobSpawnSettings.SpawnerData> list = mobsAt(level, structures, generator, category, pos, biome);
        if (SpawnScope.active(level)) {
            SpawnScope.rememberMobs(category, pos, list);
        }
        return list;
    }

    @org.spongepowered.asm.mixin.Shadow
    private static WeightedRandomList<MobSpawnSettings.SpawnerData> mobsAt(ServerLevel level, StructureManager structures, ChunkGenerator generator,
                                                                        MobCategory category, BlockPos pos, Holder<Biome> biome) {
        throw new AssertionError();
    }
}
