package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Better FPS Dist opens the vanilla Video Settings screen directly, so with Sodium installed you land on the
 * vanilla screen instead of Sodium's. Swap it for Reese's Sodium Options when installed, else Sodium's own screen.
 * Only runs when a screen opens; the screen factories are looked up once.
 */
public final class SodiumVideoScreenRedirect {
    private static final Logger LOG = LoggerFactory.getLogger("CoO");
    private static final String REESES = "me.flashyreese.mods.reeses_sodium_options.client.gui.SodiumVideoOptionsScreen";
    private static final String SODIUM = "net.caffeinemc.mods.sodium.client.gui.VideoSettingsScreen";

    private static MethodHandle factory;
    private static boolean resolved;

    private SodiumVideoScreenRedirect() {
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, false, ScreenEvent.Opening.class, SodiumVideoScreenRedirect::onOpening);
    }

    private static void onOpening(ScreenEvent.Opening event) {
        Screen requested = event.getNewScreen();
        if (!CoOConfig.betterFpsDistSodiumVideoScreen || requested == null || requested.getClass() != VideoSettingsScreen.class) {
            return;
        }
        MethodHandle make = factory();
        if (make == null) {
            return;
        }
        try {
            Screen replacement = (Screen) make.invoke(event.getCurrentScreen());
            if (replacement != null) {
                event.setNewScreen(replacement);
            }
        } catch (Throwable throwable) {
            LOG.warn("[CoO] Could not open Sodium's video settings, keeping the vanilla screen", throwable);
            factory = null;
        }
    }

    private static MethodHandle factory() {
        if (resolved) {
            return factory;
        }
        resolved = true;
        MethodHandles.Lookup lookup = MethodHandles.publicLookup();
        MethodType fromParent = MethodType.methodType(void.class, Screen.class);
        if (ModList.get().isLoaded("reeses_sodium_options")) {
            try {
                factory = lookup.findConstructor(Class.forName(REESES), fromParent).asType(MethodType.methodType(Screen.class, Screen.class));
                return factory;
            } catch (ReflectiveOperationException | LinkageError error) {
                LOG.warn("[CoO] Reese's Sodium Options screen not found, using Sodium's", error);
            }
        }
        try {
            Class<?> sodium = Class.forName(SODIUM);
            factory = lookup.findStatic(sodium, "createScreen", MethodType.methodType(Screen.class, Screen.class));
        } catch (ReflectiveOperationException | LinkageError error) {
            LOG.warn("[CoO] Sodium's video settings screen not found, Better FPS Dist will keep the vanilla screen", error);
        }
        return factory;
    }
}
