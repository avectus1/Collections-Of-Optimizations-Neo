package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/**
 * Static maps in other mods that hold on to whole worlds after the server stops. In singleplayer every
 * world you open and leave stays in memory until the game is closed. Empty them when the server stops.
 */
public final class ModLeakGuards {
    private ModLeakGuards() {
    }

    public static void register() {
        boolean alexsMobs = ModList.get().isLoaded("alexsmobs");
        NeoForge.EVENT_BUS.addListener(ServerStoppedEvent.class, e -> {
            if (alexsMobs && CoOConfig.alexsmobsLeakGuard) {
                com.misanthropy.collections_of_optimizations.mixin.alexsmobs.AlexsMobsLeakAccessor.coo$whaleSpawners().clear();
                com.misanthropy.collections_of_optimizations.mixin.alexsmobs.AMWorldDataLeakAccessor.coo$dataMap().clear();
            }
            BmdBlockCacheState.clear();
        });
    }
}
