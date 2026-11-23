package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.mixin.dungeonnowloading.DnlSoundHandlerAccessor;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Dungeon Now Loading tracks its looping entity sounds in a static map and only forgets one when it is faded
 * out on purpose. Sounds that end on their own (the mob died, unloaded, or you left the world) stay in the map
 * with their entity, and through it the old client world. Prune finished sounds every few seconds and empty
 * the map when leaving a world. A finished sound is never counted as active by DNL, so nothing it checks changes.
 */
public final class DnlClientLeakGuard {
    private static int ticks;

    private DnlClientLeakGuard() {
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(ClientPlayerNetworkEvent.LoggingOut.class, e -> {
            if (CoOConfig.dungeonnowloadingLeakGuard) {
                DnlSoundHandlerAccessor.coo$activeSounds().clear();
            }
        });
        NeoForge.EVENT_BUS.addListener(ClientTickEvent.Post.class, e -> {
            if (CoOConfig.dungeonnowloadingLeakGuard && ++ticks >= 100) {
                ticks = 0;
                prune(DnlSoundHandlerAccessor.coo$activeSounds());
            }
        });
    }

    @SuppressWarnings("unchecked")
    private static void prune(Map<?, ?> sounds) {
        Iterator<? extends Map.Entry<?, ?>> bySound = sounds.entrySet().iterator();
        while (bySound.hasNext()) {
            Map<?, ?> byEntity = (Map<?, ?>) bySound.next().getValue();
            Iterator<? extends Map.Entry<?, ?>> entities = byEntity.entrySet().iterator();
            while (entities.hasNext()) {
                Map<?, ?> byTag = (Map<?, ?>) entities.next().getValue();
                Iterator<? extends Map.Entry<?, ?>> tags = byTag.entrySet().iterator();
                while (tags.hasNext()) {
                    List<AbstractTickableSoundInstance> list = (List<AbstractTickableSoundInstance>) tags.next().getValue();
                    list.removeIf(AbstractTickableSoundInstance::isStopped);
                    if (list.isEmpty()) {
                        tags.remove();
                    }
                }
                if (byTag.isEmpty()) {
                    entities.remove();
                }
            }
            if (byEntity.isEmpty()) {
                bySound.remove();
            }
        }
    }
}
