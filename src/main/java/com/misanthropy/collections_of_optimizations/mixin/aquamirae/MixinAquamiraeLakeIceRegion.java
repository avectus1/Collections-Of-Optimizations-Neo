package com.misanthropy.collections_of_optimizations.mixin.aquamirae;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Aquamirae checks the biome inside Biome.shouldFreeze with level.getBiome(pos). When a lake is placed during
 * world generation, its ice check runs up to the edge of the generation area, where getBiome's biome blending
 * reads a chunk that isn't available and the region throws "Requested chunk unavailable during world
 * generation". The whole feature step of that chunk fails, which leaves empty chunks (seen in Alex's Caves'
 * Primordial Caves). Alex's Caves already avoids this for the lake's own biome lookup; this does the same for
 * the freeze check: if it throws there, the check is redone with the unblended biome, which needs no
 * neighbouring chunk. Nothing changes when the check works normally.
 */
@Mixin(LakeFeature.class)
public abstract class MixinAquamiraeLakeIceRegion {
    @Unique
    private static final TagKey<Biome> COO$NO_FREEZING =
            TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("aquamirae", "without_water_freezing"));

    @WrapOperation(method = "place", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/biome/Biome;shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z"))
    private boolean coo$freezeSafely(Biome biome, LevelReader level, BlockPos pos, boolean mustBeAtEdge, Operation<Boolean> original) {
        if (!CoOConfig.aquamiraeLakeIceRegionFix || !(level instanceof WorldGenRegion region)) {
            return original.call(biome, level, pos, mustBeAtEdge);
        }
        try {
            return original.call(biome, level, pos, mustBeAtEdge);
        } catch (RuntimeException e) {
            if (!coo$chunkUnavailable(e)) {
                throw e;
            }
            Holder<Biome> holder = region.getUncachedNoiseBiome(pos.getX() >> 2, pos.getY() >> 2, pos.getZ() >> 2);
            if (holder.is(COO$NO_FREEZING) || biome.warmEnoughToRain(pos)) {
                return false;
            }
            if (pos.getY() < level.getMinBuildHeight() || pos.getY() >= level.getMaxBuildHeight()
                    || level.getBrightness(LightLayer.BLOCK, pos) >= 10) {
                return false;
            }
            if (level.getFluidState(pos).getType() != Fluids.WATER || !(level.getBlockState(pos).getBlock() instanceof LiquidBlock)) {
                return false;
            }
            return !mustBeAtEdge || !(level.isWaterAt(pos.west()) && level.isWaterAt(pos.east())
                    && level.isWaterAt(pos.north()) && level.isWaterAt(pos.south()));
        }
    }

    @Unique
    private static boolean coo$chunkUnavailable(Throwable e) {
        for (Throwable t = e; t != null; t = t.getCause()) {
            String m = t.getMessage();
            if (m != null && (m.contains("Requested chunk unavailable") || m.contains("chunk out of bound"))) {
                return true;
            }
            if (t.getCause() == t) {
                break;
            }
        }
        return false;
    }
}
