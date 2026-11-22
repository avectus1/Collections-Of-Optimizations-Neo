package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import dev.emi.emi.runtime.EmiHistory;
import dev.emi.emi.screen.EmiScreenManager;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.common.NeoForge;

/**
 * EMI keeps every screen you browsed through its recipe pages in a static back/forward history.
 * Those screens are usually the inventory or a container screen, which hold the menu, the menu holds
 * the local player and the player holds the client level, so nothing in the history lets go of the
 * world you just left until EMI happens to clear it. Leaving a world now clears the history.
 */
public final class EmiHistoryRelease {

    private EmiHistoryRelease() {
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(EmiHistoryRelease::onLoggingOut);
    }

    private static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        if (!CoOConfig.emiReleaseHistoryOnLeave) {
            return;
        }
        try {
            EmiHistory.clear();
            EmiScreenManager.lastStackTooltipRendered = null;
        } catch (Throwable ignored) {
        }
    }
}
