package com.misanthropy.collections_of_optimizations.mixin.majruszsdifficulty;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * The spawn-rate event is built for every chunk and mob category on every tick, and its constructor
 * searches for the nearest player each time. Nothing that listens to the event reads that player
 * (the two listeners only look at the category, the game stage and the blood moon), so skip the search.
 */
@Mixin(targets = "com.majruszsdifficulty.lib.events.OnMobSpawnRateGet", remap = false)
public abstract class MixinSpawnRateEventNoPlayerScan {
    @Redirect(method = "<init>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getNearestPlayer(DDDDZ)Lnet/minecraft/world/entity/player/Player;"))
    private Player coo$skipPlayerScan(ServerLevel level, double x, double y, double z, double distance, boolean creative) {
        return CoOConfig.majruszsdifficultySkipSpawnRatePlayerScan ? null : level.getNearestPlayer(x, y, z, distance, creative);
    }
}
