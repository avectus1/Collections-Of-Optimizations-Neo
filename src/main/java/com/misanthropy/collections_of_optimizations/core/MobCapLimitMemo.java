package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

/**
 * MobCategory.getMaxInstancesPerChunk is asked per chunk and per category while spawning. With Majrusz's
 * Difficulty each call builds and dispatches an event whose answer only depends on the category, the game
 * stage and whether a blood moon is running, so keep each category's answer for the rest of the tick.
 */
public final class MobCapLimitMemo {
    private static final int N = MobCategory.values().length;
    private static final int[] VALUES = new int[N];
    private static final int[] TICKS = new int[N];
    private static Thread owner;

    static {
        java.util.Arrays.fill(TICKS, Integer.MIN_VALUE);
    }

    private MobCapLimitMemo() {
    }

    public static int get(MobCategory category) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null || !server.isSameThread()) {
            return category.getMaxInstancesPerChunk();
        }
        if (owner != Thread.currentThread()) {
            owner = Thread.currentThread();
            java.util.Arrays.fill(TICKS, Integer.MIN_VALUE);
        }
        int tick = server.getTickCount();
        int i = category.ordinal();
        if (TICKS[i] == tick) {
            return VALUES[i];
        }
        int v = category.getMaxInstancesPerChunk();
        VALUES[i] = v;
        TICKS[i] = tick;
        return v;
    }
}
