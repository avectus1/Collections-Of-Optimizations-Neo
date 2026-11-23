package com.misanthropy.collections_of_optimizations.mixin.subtleeffects;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.SeBiomeMemo;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * For each random block the client animates, vanilla and Subtle Effects (biome particles, fireflies) each look up the
 * biome of that same block. Look it up once per block and share it.
 */
@Mixin(value = ClientLevel.class, priority = 1500, remap = false)
public abstract class MixinSeClientLevelBiome implements SeBiomeMemo {
    @Unique
    private long coo$memoPos;
    @Unique
    private Holder<Biome> coo$memoBiome;

    @Inject(method = "doAnimateTick", at = @At("HEAD"))
    private void coo$resetMemo(int x, int y, int z, int range, RandomSource random, Block marker, BlockPos.MutableBlockPos pos, CallbackInfo ci) {
        this.coo$memoBiome = null;
    }

    @Redirect(method = "doAnimateTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getBiome(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/Holder;"))
    private Holder<Biome> coo$sharedBiome(ClientLevel level, BlockPos pos) {
        Holder<Biome> biome = this.coo$animateBiome(pos);
        this.coo$memoBiome = null;
        return biome;
    }

    @Override
    public Holder<Biome> coo$animateBiome(BlockPos pos) {
        if (!CoOConfig.subtleeffectsSharedBiome) {
            return ((ClientLevel) (Object) this).getBiome(pos);
        }
        long key = pos.asLong();
        Holder<Biome> biome = this.coo$memoBiome;
        if (biome == null || key != this.coo$memoPos) {
            biome = ((ClientLevel) (Object) this).getBiome(pos);
            this.coo$memoBiome = biome;
            this.coo$memoPos = key;
        }
        return biome;
    }
}
