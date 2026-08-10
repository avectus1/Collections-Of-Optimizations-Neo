package com.misanthropy.collections_of_optimizations.core;

import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.atomic.AtomicInteger;

public final class CuriosSlotCache {
    private static final AtomicInteger GENERATION = new AtomicInteger(1);

    private CuriosSlotCache() {
    }

    public static int generation() {
        return GENERATION.get();
    }

    public static void invalidate() {
        GENERATION.incrementAndGet();
    }

    public static void register() {
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.addListener(CuriosSlotCache::onAddReloadListener);
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.addListener(CuriosSlotCache::onDatapackSync);
    }

    private static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new SimplePreparableReloadListener<Void>() {
            @Override
            protected Void prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
                return null;
            }

            @Override
            protected void apply(Void unused, ResourceManager resourceManager, ProfilerFiller profiler) {
                invalidate();
            }
        });
    }

    private static void onDatapackSync(OnDatapackSyncEvent event) {
        invalidate();
    }
}
