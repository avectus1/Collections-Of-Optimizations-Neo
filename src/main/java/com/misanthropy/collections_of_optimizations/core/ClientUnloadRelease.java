package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.LevelEvent;

/**
 * Client-side objects that live for the whole game (models, renderers) but remember the last mob they drew,
 * which keeps that mob's whole client world alive after you leave it or change dimension. Each registers
 * here and forgets the mob when a client world unloads.
 */
public final class ClientUnloadRelease {
    public interface Releasable {
        void coo$release();
    }

    private static final Set<Releasable> ANIMATORS = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap<>()));

    private ClientUnloadRelease() {
    }

    private static final List<Runnable> HOOKS = new java.util.concurrent.CopyOnWriteArrayList<>();

    public static void onUnload(Runnable hook) {
        HOOKS.add(hook);
    }

    public static void track(Releasable animator) {
        ANIMATORS.add(animator);
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(LevelEvent.Unload.class, e -> {
            if (CoOConfig.clientUnloadRelease && e.getLevel().isClientSide()) {
                List<Releasable> copy;
                synchronized (ANIMATORS) {
                    copy = new ArrayList<>(ANIMATORS);
                }
                for (Releasable animator : copy) {
                    animator.coo$release();
                }
                for (Runnable hook : HOOKS) {
                    hook.run();
                }
            }
        });
    }
}
