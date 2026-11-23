package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * Subtle Effects checks an entity against an outer range (with a chunk lookup) and right after against an inner range.
 * The second check only needs the distance: the chunk was just found loaded by the first.
 */
public final class SubtleEffectsRange {
    private SubtleEffectsRange() {
    }

    public static boolean innerRange(Entity entity, int range) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return false;
        }
        return player.is(entity) || player.position().closerThan(entity.position(), range);
    }
}
