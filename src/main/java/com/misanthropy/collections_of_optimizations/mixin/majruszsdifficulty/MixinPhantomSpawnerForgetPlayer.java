package com.misanthropy.collections_of_optimizations.mixin.majruszsdifficulty;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Majrusz's Difficulty notes the player it is checking during the phantom spawner's tick and never clears it, so
 * the last player checked (with their connection, inventory and data) stays in memory after they leave or respawn.
 * It is only read later in the same tick, so clear it when the tick ends.
 */
@Mixin(value = PhantomSpawner.class, priority = 1100, remap = false)
public abstract class MixinPhantomSpawnerForgetPlayer {
    @Dynamic("Added by Majrusz's Difficulty")
    @Shadow(remap = false)
    private ServerPlayer majruszsdifficulty$lastPlayer;

    @Inject(method = "tick", at = @At("RETURN"))
    private void coo$forget(ServerLevel level, boolean spawnEnemies, boolean spawnFriendlies, CallbackInfoReturnable<Integer> cir) {
        if (CoOConfig.majruszsdifficultyForgetPhantomPlayer) {
            this.majruszsdifficulty$lastPlayer = null;
        }
    }
}
