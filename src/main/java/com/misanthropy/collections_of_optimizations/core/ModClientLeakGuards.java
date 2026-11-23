package com.misanthropy.collections_of_optimizations.core;

import com.github.alexmodguy.alexscaves.client.render.misc.CaveMapRenderer;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Client-side leaks: Citadel's guide-book buttons keep the mob models they draw (and through them the old
 * client world) forever, and Alex's Caves keeps a map texture for every cave map item object it ever drew.
 */
public final class ModClientLeakGuards {
    private static final long CAVE_MAP_IDLE_MS = 30_000L;
    private static int ticks;
    private static int xaeroTicks;

    private ModClientLeakGuards() {
    }

    public static void register() {
        boolean citadel = ModList.get().isLoaded("citadel");
        boolean alexsCaves = ModList.get().isLoaded("alexscaves");
        boolean xaeroMinimap = ModList.get().isLoaded("xaerominimap") && ModList.get().isLoaded("xaeroworldmap");
        NeoForge.EVENT_BUS.addListener(ClientPlayerNetworkEvent.LoggingOut.class, e -> {
            if (citadel && CoOConfig.citadelLeakGuard) {
                com.misanthropy.collections_of_optimizations.mixin.citadel.CitadelEntityLinkAccessor.coo$renderedEntities().clear();
            }
            if (alexsCaves && CoOConfig.alexscavesCaveMapRelease) {
                CaveMaps.release(Long.MIN_VALUE);
            }
            if (xaeroMinimap && CoOConfig.xaeroSlimeSeedsLeakGuard) {
                com.misanthropy.collections_of_optimizations.mixin.xaerominimap.XaeroSlimeSeedsAccessor.coo$seedsUsed().clear();
            }
        });
        if (xaeroMinimap) {
            NeoForge.EVENT_BUS.addListener(ClientTickEvent.Post.class, e -> {
                // The minimap remembers which world-map tiles it drew slime chunks on, keyed by the tile itself,
                // and never forgets one, which keeps unloaded map tiles alive. Forgetting only means the
                // slime chunk overlay is worked out again for that tile.
                if (CoOConfig.xaeroSlimeSeedsLeakGuard && ++xaeroTicks >= 200) {
                    xaeroTicks = 0;
                    java.util.HashMap<?, Long> seeds = com.misanthropy.collections_of_optimizations.mixin.xaerominimap.XaeroSlimeSeedsAccessor.coo$seedsUsed();
                    if (seeds.size() > 1024) {
                        seeds.clear();
                    }
                }
            });
        }
        if (alexsCaves) {
            NeoForge.EVENT_BUS.addListener(ClientTickEvent.Post.class, e -> {
                if (CoOConfig.alexscavesCaveMapRelease && ++ticks >= 100) {
                    ticks = 0;
                    CaveMaps.release(Util.getMillis() - CAVE_MAP_IDLE_MS);
                }
            });
        }
    }

    private static final class CaveMaps {
        static void release(long usedBefore) {
            sweep(com.misanthropy.collections_of_optimizations.mixin.alexscaves.CaveMapRendererAccessor.coo$hand(), usedBefore);
            sweep(com.misanthropy.collections_of_optimizations.mixin.alexscaves.CaveMapRendererAccessor.coo$frame(), usedBefore);
        }

        private static void sweep(Map<ItemStack, CaveMapRenderer> maps, long usedBefore) {
            Iterator<Map.Entry<ItemStack, CaveMapRenderer>> it = maps.entrySet().iterator();
            while (it.hasNext()) {
                CaveMapRendererSlot slot = (CaveMapRendererSlot) it.next().getValue();
                if (usedBefore == Long.MIN_VALUE || slot.coo$lastUsed() < usedBefore) {
                    it.remove();
                    ResourceLocation id = slot.coo$textureId();
                    if (id != null) {
                        Minecraft.getInstance().getTextureManager().release(id);
                    }
                }
            }
        }
    }
}
