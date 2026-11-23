package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.server.MinecraftServer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

/** Every minute, drops structure templates that have not been used for the configured number of minutes. */
public final class StructureTemplateRelease {
    public interface Sweepable {
        int coo$sweep(long olderThanNanos);
    }

    private static int ticks;
    private static final boolean LOG = Boolean.getBoolean("coo.verifyTemplates");

    private StructureTemplateRelease() {
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(ServerTickEvent.Post.class, e -> {
            if (!CoOConfig.vanillaReleaseStructureTemplates || ++ticks < CoOConfig.vanillaStructureTemplateSweepTicks) {
                return;
            }
            ticks = 0;
            MinecraftServer server = e.getServer();
            long ttl = CoOConfig.vanillaStructureTemplateIdleSeconds * 1_000_000_000L;
            int dropped = ((Sweepable) server.getStructureManager()).coo$sweep(ttl);
            if (dropped > 0 && LOG) {
                org.slf4j.LoggerFactory.getLogger("coo-verify").info("[coo-verify] released {} structure templates", dropped);
            }
        });
    }
}
