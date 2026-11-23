package com.misanthropy.collections_of_optimizations.core;

import com.eeeab.eeeabsmobs.server.entity.mob.immortal.EntityImmortalBoss;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.mixin.eeeabsmobs.ImmortalBossTargetsAccessor;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * EEEAB's Immortal boss keeps a list of players it is fighting and only refreshes it now and then, so a player who
 * logged out or respawned stays in that list (with their whole old player object) for as long as the boss lives.
 * Drop gone players from the list when someone leaves or respawns.
 */
public final class EeeabTargetRelease {
    private EeeabTargetRelease() {
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerLoggedOutEvent.class, e -> prune(e.getEntity()));
        NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerRespawnEvent.class, e -> prune(null));
    }

    private static void prune(Entity leaving) {
        if (!CoOConfig.eeeabsmobsBossTargetRelease || !(leaving == null || leaving.level() instanceof ServerLevel)) {
            return;
        }
        net.minecraft.server.MinecraftServer server = net.neoforged.neoforge.server.ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return;
        }
        for (ServerLevel level : server.getAllLevels()) {
            for (EntityImmortalBoss boss : level.getEntities(EntityTypeTest.forClass(EntityImmortalBoss.class), b -> true)) {
                ImmortalBossTargetsAccessor access = (ImmortalBossTargetsAccessor) boss;
                List<LivingEntity> targets = access.coo$targets();
                if (targets.isEmpty()) {
                    continue;
                }
                List<LivingEntity> kept = targets.stream().filter(t -> t != leaving && !t.isRemoved()).toList();
                if (kept.size() != targets.size()) {
                    access.coo$setTargets(kept);
                }
            }
        }
    }
}
