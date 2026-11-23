package com.misanthropy.collections_of_optimizations.mixin.subtleeffects;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import einstein.subtle_effects.ticking.tickers.ChestBlockEntityTicker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Subtle Effects asks for a block entity at every random block the client animates, looking for chests. Blocks that
 * cannot have a block entity (almost all of them) never have one, so skip the lookup for them.
 */
@Mixin(value = ChestBlockEntityTicker.class, remap = false)
public abstract class MixinSeChestBlockEntity {
    @Redirect(method = "trySpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;"))
    private static BlockEntity coo$onlyWithBlockEntity(Level level, BlockPos pos, Level l, BlockPos p, BlockState state) {
        if (CoOConfig.subtleeffectsChestLookup && !state.hasBlockEntity()) {
            return null;
        }
        return level.getBlockEntity(pos);
    }
}
