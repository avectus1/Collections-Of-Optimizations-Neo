package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.mixin.morequesttypes.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

/**
 * More Quest Types buffers each player's recent hits, casts, interactions... per game tick, so quest tasks can
 * read them in the same tick. Old ticks are only dropped when that player triggers the same kind of event again,
 * so the last few stay forever, together with the entities and items they mention (including the player's own
 * object from before a respawn or rejoin). Drop ticks older than ten seconds, and a player's buffers when they leave.
 */
public final class MqtBufferSweep {
    private static final long KEEP_TICKS = 200L;
    private static int ticks;

    private MqtBufferSweep() {
    }

    private static List<Map<UUID, ? extends Map<Long, ?>>> maps() {
        List<Map<UUID, ? extends Map<Long, ?>>> out = new ArrayList<>();
        out.add(MqtBreakBlockAccessor.coo$buckets());
        out.add(MqtCommandAccessor.coo$buckets());
        out.add(MqtInteractAccessor.coo$buckets());
        out.add(MqtInteractAccessor.coo$seen());
        out.add(MqtReceiveDamageAccessor.coo$buckets());
        out.add(MqtSpellCastAccessor.coo$buckets());
        out.add(MqtTameAccessor.coo$buckets());
        out.add(MqtUseItemAccessor.coo$buckets());
        out.add(MqtUseItemAccessor.coo$seen());
        out.add(MqtDamageAccessor.coo$buckets());
        return out;
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(ServerTickEvent.Post.class, e -> {
            if (!CoOConfig.morequesttypesBufferSweep || ++ticks < 200) {
                return;
            }
            ticks = 0;
            MinecraftServer server = e.getServer();
            ServerLevel overworld = server.overworld();
            long keepFrom = overworld.getGameTime() - KEEP_TICKS;
            for (Map<UUID, ? extends Map<Long, ?>> map : maps()) {
                Iterator<? extends Map<Long, ?>> it = map.values().iterator();
                while (it.hasNext()) {
                    Map<Long, ?> byTick = it.next();
                    byTick.keySet().removeIf(t -> t < keepFrom);
                    if (byTick.isEmpty()) {
                        it.remove();
                    }
                }
            }
        });
        NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerLoggedOutEvent.class, e -> {
            if (CoOConfig.morequesttypesBufferSweep) {
                UUID id = e.getEntity().getUUID();
                for (Map<UUID, ? extends Map<Long, ?>> map : maps()) {
                    map.remove(id);
                }
            }
        });
        NeoForge.EVENT_BUS.addListener(ServerStoppedEvent.class, e -> {
            if (CoOConfig.morequesttypesBufferSweep) {
                for (Map<UUID, ? extends Map<Long, ?>> map : maps()) {
                    map.clear();
                }
            }
        });
    }
}
