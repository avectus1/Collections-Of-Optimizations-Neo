package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;

/**
 * Remembers when the last frame with a glowing entity outline was drawn. Brute Force Culling hides chunks
 * and entities around glowing entities in some packs; while outlines are on screen (and for a short moment
 * after, so its culling map catches up) its culling is bypassed and everything is drawn normally.
 */
public final class BfcGlowGuard {
    private static final long HOLD_NANOS = 750_000_000L;
    private static volatile long lastOutlineFrame;

    private BfcGlowGuard() {
    }

    public static void outlineFrame() {
        lastOutlineFrame = System.nanoTime();
    }

    public static boolean active() {
        long last = lastOutlineFrame;
        return CoOConfig.bfcNoCullWhileGlowing && last != 0L && System.nanoTime() - last < HOLD_NANOS;
    }
}
