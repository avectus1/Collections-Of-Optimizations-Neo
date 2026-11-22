package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;

/**
 * Lives only for the length of one NaturalSpawner.spawnCategoryForPosition call on the server thread.
 * Inside that call nothing can unload a chunk or move a ticket, so a fully loaded chunk looked up once
 * can be handed back for every later biome / structure lookup in the same call, which skips the
 * chunk cache (and every mod wrapper around it) for each spawn attempt.
 */
public final class SpawnScope {
    private static ServerLevel level;
    private static Thread thread;
    private static int depth;
    private static BiomeManager biomes;
    private static final BiomeManager.NoiseBiomeSource SOURCE = SpawnScope::noiseBiome;

    private static final int SLOTS = 16;
    private static final long[] chunkKeys = new long[SLOTS];
    private static final ChunkAccess[] chunks = new ChunkAccess[SLOTS];
    private static int chunkCount;

    private static final long[] allowKeys = new long[SLOTS];
    private static final boolean[] allowValues = new boolean[SLOTS];
    private static int allowCount;

    private static long mobsKey;
    private static MobCategory mobsCategory;
    private static WeightedRandomList<MobSpawnSettings.SpawnerData> mobsValue;

    public static final boolean VERIFY = Boolean.getBoolean("coo.verifySpawn");
    private static long checks;
    private static long mismatches;

    public static void verify(boolean same, String what) {
        checks++;
        if (!same) {
            mismatches++;
            org.slf4j.LoggerFactory.getLogger("coo-verify").warn("[coo-verify] spawn scope mismatch in {}", what);
        }
        if ((checks & 1023) == 0) {
            org.slf4j.LoggerFactory.getLogger("coo-verify").info("[coo-verify] spawn scope checks={} mismatches={}", checks, mismatches);
        }
    }

    private SpawnScope() {
    }

    /** @return true if this call opened the scope and must close it. */
    public static boolean enter(ServerLevel l, ChunkAccess chunk) {
        if (depth > 0) {
            if (level == l && thread == Thread.currentThread()) {
                depth++;
                return true;
            }
            return false;
        }
        level = l;
        thread = Thread.currentThread();
        depth = 1;
        biomes = null;
        chunkCount = 0;
        allowCount = 0;
        mobsValue = null;
        mobsCategory = null;
        if (isFull(chunk)) {
            ChunkPos p = chunk.getPos();
            chunkKeys[0] = ChunkPos.asLong(p.x, p.z);
            chunks[0] = chunk;
            chunkCount = 1;
        }
        return true;
    }

    public static void exit() {
        if (--depth <= 0) {
            depth = 0;
            level = null;
            thread = null;
            biomes = null;
            mobsValue = null;
            mobsCategory = null;
            for (int i = 0; i < chunkCount; i++) {
                chunks[i] = null;
            }
            chunkCount = 0;
            allowCount = 0;
        }
    }

    public static boolean active(LevelAccessor l) {
        return depth > 0 && l == level && Thread.currentThread() == thread;
    }

    private static ChunkAccess unwrap(ChunkAccess c) {
        return c instanceof ImposterProtoChunk imposter ? imposter.getWrapped() : c;
    }

    private static boolean isFull(ChunkAccess c) {
        return c instanceof LevelChunk || c instanceof ImposterProtoChunk;
    }

    private static ChunkAccess cached(int cx, int cz) {
        long key = ChunkPos.asLong(cx, cz);
        for (int i = 0; i < chunkCount; i++) {
            if (chunkKeys[i] == key) {
                return chunks[i];
            }
        }
        return null;
    }

    private static void remember(int cx, int cz, ChunkAccess c) {
        if (c != null && chunkCount < SLOTS && isFull(c)) {
            chunkKeys[chunkCount] = ChunkPos.asLong(cx, cz);
            chunks[chunkCount++] = c;
        }
    }

    /** Same as LevelReader.getNoiseBiome: chunk at BIOMES without loading, else the uncached biome. */
    private static Holder<Biome> noiseBiome(int qx, int qy, int qz) {
        ServerLevel l = level;
        int cx = QuartPos.toSection(qx);
        int cz = QuartPos.toSection(qz);
        ChunkAccess c = cached(cx, cz);
        if (c == null) {
            c = l.getChunk(cx, cz, ChunkStatus.BIOMES, false);
            remember(cx, cz, c);
        }
        return c != null ? c.getNoiseBiome(qx, qy, qz) : l.getUncachedNoiseBiome(qx, qy, qz);
    }

    public static Holder<Biome> biome(ServerLevel l, BlockPos pos) {
        BiomeManager m = biomes;
        if (m == null) {
            m = l.getBiomeManager().withDifferentSource(SOURCE);
            biomes = m;
        }
        Holder<Biome> b = m.getBiome(pos);
        if (VERIFY) {
            verify(b == l.getBiome(pos), "biome");
        }
        return b;
    }

    /** Same as LevelAccessor.getChunk(x, z, status) for a chunk that is already fully loaded. */
    public static ChunkAccess chunk(LevelAccessor l, int cx, int cz, ChunkStatus status) {
        ChunkAccess c = cached(cx, cz);
        if (c != null) {
            if (VERIFY) {
                ChunkAccess real = l.getChunk(cx, cz, status);
                verify(unwrap(real) == unwrap(c), "chunk");
            }
            return c;
        }
        c = l.getChunk(cx, cz, status);
        remember(cx, cz, c);
        return c;
    }

    public static boolean spawningAllowed(ServerLevel l, BlockPos pos) {
        long key = ChunkPos.asLong(pos);
        for (int i = 0; i < allowCount; i++) {
            if (allowKeys[i] == key) {
                if (VERIFY) {
                    verify(allowValues[i] == l.isNaturalSpawningAllowed(pos), "allowed");
                }
                return allowValues[i];
            }
        }
        boolean v = l.isNaturalSpawningAllowed(pos);
        if (allowCount < SLOTS) {
            allowKeys[allowCount] = key;
            allowValues[allowCount++] = v;
        }
        return v;
    }

    public static WeightedRandomList<MobSpawnSettings.SpawnerData> lastMobs(MobCategory category, BlockPos pos) {
        return mobsValue != null && mobsCategory == category && mobsKey == pos.asLong() ? mobsValue : null;
    }

    public static void rememberMobs(MobCategory category, BlockPos pos, WeightedRandomList<MobSpawnSettings.SpawnerData> value) {
        mobsCategory = category;
        mobsKey = pos.asLong();
        mobsValue = value;
    }
}
