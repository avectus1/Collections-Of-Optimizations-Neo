package com.misanthropy.collections_of_optimizations.core;

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/** Counts stopped servers so each thread can tell its Structurify height cache belongs to an older world. */
public final class StructurifyHeightCacheEpoch {
    private static volatile int epoch;
    private static final ThreadLocal<int[]> SEEN = ThreadLocal.withInitial(() -> new int[1]);

    private StructurifyHeightCacheEpoch() {
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(ServerStoppedEvent.class, event -> epoch++);
    }

    /** True once per thread after each server stop. */
    public static boolean isStale() {
        int current = epoch;
        int[] seen = SEEN.get();
        if (seen[0] != current) {
            seen[0] = current;
            return true;
        }
        return false;
    }
}
