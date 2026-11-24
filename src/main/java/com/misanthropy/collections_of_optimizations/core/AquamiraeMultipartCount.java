package com.misanthropy.collections_of_optimizations.core;

import dev.obscuria.aquamirae.api.common.entity.MultipartEntity;
import java.util.concurrent.atomic.AtomicInteger;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;

/**
 * Counts Aquamirae multipart entities (Shellbacks) loaded on either side. While there are none, the
 * Level.getEntities hook has nothing to add and can skip its per-entity wrapper entirely. Errs on the side
 * of "maybe some": a count that drifts high only means the full check keeps running.
 */
public final class AquamiraeMultipartCount {
    private static final AtomicInteger LOADED = new AtomicInteger();

    private AquamiraeMultipartCount() {
    }

    public static boolean any() {
        return LOADED.get() > 0;
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, true, EntityJoinLevelEvent.class, event -> {
            if (event.getEntity() instanceof MultipartEntity) {
                LOADED.incrementAndGet();
            }
        });
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, true, EntityLeaveLevelEvent.class, event -> {
            if (event.getEntity() instanceof MultipartEntity && LOADED.decrementAndGet() < 0) {
                LOADED.set(0);
            }
        });
    }
}
