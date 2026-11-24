package com.misanthropy.collections_of_optimizations.mixin.enderscape;

import java.util.Map;
import net.bunten.enderscape.registry.EnderscapeStructureMusic;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = EnderscapeStructureMusic.class, remap = false)
public interface EnderscapeStructureMusicAccessor {
    @Accessor("playerTimers")
    static Map<ServerPlayer, Integer> coo$timers() {
        throw new AssertionError();
    }

    @Accessor("playerStructures")
    static Map<ServerPlayer, ResourceLocation> coo$structures() {
        throw new AssertionError();
    }
}
