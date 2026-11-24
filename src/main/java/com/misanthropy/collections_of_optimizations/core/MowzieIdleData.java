package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.bobmowzie.mowziesmobs.server.capability.AbilityData;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import com.bobmowzie.mowziesmobs.server.capability.FrozenData;
import com.bobmowzie.mowziesmobs.server.capability.LivingData;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrozenController;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Mowzie's Mobs gives every living entity in the world (cows, zombies, everything) three saved data objects the first
 * tick it exists, ticks them every tick, and writes them into every saved chunk, even though for almost every entity
 * they never hold anything. While an entity has none of that data yet and nothing Mowzie-related is happening to it
 * (not frozen, not a Mowzie mob or player), ticking fresh data changes nothing, so hand out a shared empty one instead
 * of creating and saving a copy per entity. The real data is still created the moment anything actually needs it.
 */
public final class MowzieIdleData {
    private static final FrozenData IDLE_FROZEN = new FrozenData();
    private static final LivingData IDLE_LIVING = new LivingData();
    private static final AbilityData IDLE_ABILITY = new AbilityData();

    private MowzieIdleData() {
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Object getForTick(Entity entity, DeferredHolder type) {
        if (CoOConfig.mowziesmobsIdleEntityData && entity instanceof LivingEntity living && !entity.hasData((java.util.function.Supplier<AttachmentType<Object>>) (Object) type)) {
            if (type == DataHandler.LIVING_DATA) {
                return IDLE_LIVING;
            }
            if (type == DataHandler.ABILITY_DATA && !(entity instanceof Player) && !(entity instanceof MowzieGeckoEntity)) {
                return IDLE_ABILITY;
            }
            if (type == DataHandler.FROZEN_DATA && idleFrozen(living)) {
                return IDLE_FROZEN;
            }
        }
        return DataHandler.getData(entity, type);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Object getForRender(Entity entity, DeferredHolder type) {
        if (CoOConfig.mowziesmobsIdleEntityData && type == DataHandler.FROZEN_DATA && entity instanceof LivingEntity living
                && !entity.hasData((java.util.function.Supplier<AttachmentType<Object>>) (Object) type) && idleFrozen(living)) {
            return IDLE_FROZEN;
        }
        return DataHandler.getData(entity, type);
    }

    private static boolean idleFrozen(LivingEntity entity) {
        return !entity.hasEffect(EffectHandler.FROZEN) && !(entity.getVehicle() instanceof EntityFrozenController);
    }
}
