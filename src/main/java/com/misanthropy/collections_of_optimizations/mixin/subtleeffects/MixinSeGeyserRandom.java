package com.misanthropy.collections_of_optimizations.mixin.subtleeffects;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import einstein.subtle_effects.ticking.GeyserManager;
import einstein.subtle_effects.ticking.tickers.geyser.GeyserTicker;
import einstein.subtle_effects.ticking.tickers.geyser.GeyserType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * In the Nether, Subtle Effects makes a brand new random generator for every block it looks at (several per block,
 * hundreds of blocks per tick) just to roll a tiny geyser chance. Reuse one generator for the roll, and only make a
 * fresh one, in exactly the same state, for the rare roll that succeeds. Same geysers in the same places.
 */
@Mixin(value = GeyserManager.class, remap = false)
public abstract class MixinSeGeyserRandom {
    @Unique
    private static final LegacyRandomSource coo$scratch = new LegacyRandomSource(0L);
    @Unique
    private static long coo$seed;

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;create(J)Lnet/minecraft/util/RandomSource;"))
    private static RandomSource coo$reuse(long seed) {
        if (!CoOConfig.subtleeffectsGeyserRandom) {
            return RandomSource.create(seed);
        }
        coo$seed = seed;
        coo$scratch.setSeed(seed);
        return coo$scratch;
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Leinstein/subtle_effects/ticking/tickers/geyser/GeyserTicker;trySpawn(Leinstein/subtle_effects/ticking/tickers/geyser/GeyserType;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V"))
    private static void coo$fresh(GeyserType type, Level level, BlockPos pos, RandomSource random) {
        if (random == coo$scratch) {
            random = RandomSource.create(coo$seed);
            random.nextDouble();
        }
        GeyserTicker.trySpawn(type, level, pos, random);
    }
}
