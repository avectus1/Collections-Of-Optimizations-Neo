package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.mixin.dungeonnowloading.DnlVertexEffectAccessor;
import dev.hexnowloading.dungeonnowloading.potion.VertexTransmissionEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/**
 * Dungeon Now Loading's Vertex Transmission effect keeps a node per affected mob (holding the mob and the
 * mobs it is linked to) in static maps, and only drops it when the mob is removed while still affected.
 * When the effect simply runs out or is cured, the node stays forever, pinning those mobs. Drop it then too,
 * and empty the maps when the server stops. One of the four maps is never cleaned at all; clean it as well.
 */
public final class DnlLeakGuard {
    private DnlLeakGuard() {
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(MobEffectEvent.Expired.class, e -> onEnd(e.getEntity(), e.getEffectInstance()));
        NeoForge.EVENT_BUS.addListener(MobEffectEvent.Remove.class, e -> onEnd(e.getEntity(), e.getEffectInstance()));
        NeoForge.EVENT_BUS.addListener(ServerStoppedEvent.class, e -> {
            if (CoOConfig.dungeonnowloadingLeakGuard) {
                DnlVertexEffectAccessor.coo$nodes().clear();
                DnlVertexEffectAccessor.coo$damageTicks().clear();
                DnlVertexEffectAccessor.coo$reconnection().clear();
                DnlVertexEffectAccessor.coo$noConnection().clear();
            }
        });
    }

    private static void onEnd(LivingEntity entity, MobEffectInstance instance) {
        if (!CoOConfig.dungeonnowloadingLeakGuard || instance == null || entity == null || entity.level().isClientSide()) {
            return;
        }
        MobEffect effect = instance.getEffect().value();
        if (effect instanceof VertexTransmissionEffect vertex) {
            ((DnlVertexEffectAccessor) vertex).coo$cleanup(entity);
            DnlVertexEffectAccessor.coo$noConnection().remove(entity.getUUID());
        }
    }
}
