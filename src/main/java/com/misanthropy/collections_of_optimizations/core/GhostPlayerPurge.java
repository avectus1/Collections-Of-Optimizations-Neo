package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.mixin.vanilla.ChunkMapAccessor;
import com.mojang.logging.LogUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public final class GhostPlayerPurge {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int SWEEP_INTERVAL = 100;

    private static int timer;

    private GhostPlayerPurge() {
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(GhostPlayerPurge::onServerTick);
    }

    private static void onServerTick(ServerTickEvent.Post event) {
        if (!CoOConfig.vanillaPurgeGhostPlayers
                || ++timer < SWEEP_INTERVAL) {
            return;
        }
        timer = 0;

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return;
        }
        PlayerList playerList = server.getPlayerList();

        for (ServerLevel level : server.getAllLevels()) {
            ChunkMapAccessor chunkMap = (ChunkMapAccessor) level.getChunkSource().chunkMap;

            List<ServerPlayer> ghosts = null;
            for (ServerPlayer player : chunkMap.coo$getPlayerMap().getAllPlayers()) {
                if (playerList.getPlayer(player.getUUID()) != player) {
                    if (ghosts == null) {
                        ghosts = new ArrayList<>();
                    }
                    ghosts.add(player);
                }
            }
            if (ghosts == null) {
                continue;
            }

            for (ServerPlayer ghost : ghosts) {
                LOGGER.warn("Purging ghost player {} from {} at {} {} {}; SOMETHING removed them without "
                                + "releasing their chunk tickets.",
                        ghost.getGameProfile().getName(), level.dimension().location(),
                        ghost.getBlockX(), ghost.getBlockY(), ghost.getBlockZ());
                chunkMap.coo$removeEntity(ghost);
            }
        }
    }
}
