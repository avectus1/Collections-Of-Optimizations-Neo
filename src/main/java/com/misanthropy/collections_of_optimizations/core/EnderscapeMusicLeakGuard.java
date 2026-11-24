package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.mixin.enderscape.EnderscapeStructureMusicAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * Enderscape's structure music keeps players in static maps keyed by the ServerPlayer object and only removes
 * them on logout. Dying or leaving the End makes a new ServerPlayer, so the old one (and what it references)
 * stayed in the map until the server stopped. Drop the old object when the player is replaced.
 */
public final class EnderscapeMusicLeakGuard {
    private EnderscapeMusicLeakGuard() {
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(PlayerEvent.Clone.class, event -> {
            if (CoOConfig.enderscapeMusicPlayerLeak && event.getOriginal() instanceof ServerPlayer old) {
                try {
                    EnderscapeStructureMusicAccessor.coo$timers().remove(old);
                    EnderscapeStructureMusicAccessor.coo$structures().remove(old);
                } catch (Throwable ignored) {
                }
            }
        });
    }
}
