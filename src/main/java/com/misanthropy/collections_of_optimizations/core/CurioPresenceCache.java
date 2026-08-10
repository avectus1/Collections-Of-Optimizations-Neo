package com.misanthropy.collections_of_optimizations.core;

import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public final class CurioPresenceCache {
    private static final AtomicLong GENERATION = new AtomicLong(1);

    private CurioPresenceCache() {
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(CurioPresenceCache::onCurioChanged);
    }

    private static void onCurioChanged(CurioChangeEvent event) {
        if (event.getEntity() instanceof CurioPresenceHolder holder) {
            holder.coo$invalidateCurioPresence();
        }
        GENERATION.incrementAndGet();
    }

    public static boolean mayHaveEquipped(LivingEntity entity, Item item) {
        if (entity == null || item == null || !(entity instanceof CurioPresenceHolder holder)) {
            return true;
        }

        long stamp = GENERATION.get();
        Set<Item> cached = holder.coo$curioPresence();
        if (cached == null || holder.coo$curioPresenceStamp() != stamp) {
            cached = build(entity);
            if (cached == null) {
                return true;
            }
            holder.coo$storeCurioPresence(cached, stamp);
        }
        return cached.contains(item);
    }

    private static Set<Item> build(LivingEntity entity) {
        ICuriosItemHandler handler = CuriosApi.getCuriosInventory(entity).orElse(null);
        if (handler == null) {
            return null;
        }

        Set<Item> items = new ReferenceOpenHashSet<>(8);
        try {
            for (ICurioStacksHandler stacksHandler : handler.getCurios().values()) {
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        items.add(stack.getItem());
                    }
                }
            }
        } catch (RuntimeException exception) {
            return null;
        }
        return items;
    }
}
