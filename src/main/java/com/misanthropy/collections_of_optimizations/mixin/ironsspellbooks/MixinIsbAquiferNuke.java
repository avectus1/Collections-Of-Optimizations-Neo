package com.misanthropy.collections_of_optimizations.mixin.ironsspellbooks;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.redspace.ironsspellbooks.worldgen.IExtendedNoiseChunk;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Stand-in for Iron's NoiseChunkMixin (which keeps water and lava out of its structures). Iron's version runs
 * a cancellable inject, with a callback object, on every single block of terrain generation; this one is a
 * plain return-value hook that returns immediately for the vast majority of chunks with no Iron's structure.
 * Same blocks either way. Only applied when Iron's own mixin was swapped out (needs MixinSquared).
 */
@Mixin(value = NoiseChunk.class, remap = false)
public abstract class MixinIsbAquiferNuke implements IExtendedNoiseChunk {
    @Unique
    private IExtendedNoiseChunk.AquifierNuke coo$nuke;
    @Unique
    private BlockState coo$defaultBlock;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void coo$captureDefaultBlock(int cellCountXZ, RandomState random, int firstNoiseX, int firstNoiseZ, NoiseSettings noiseSettings,
                                        DensityFunctions.BeardifierOrMarker beardifier, NoiseGeneratorSettings settings,
                                        Aquifer.FluidPicker fluidPicker, Blender blender, CallbackInfo ci) {
        this.coo$defaultBlock = settings.defaultBlock();
    }

    @ModifyReturnValue(method = "getInterpolatedState", at = @At("RETURN"))
    private BlockState coo$keepFluidsOutOfStructures(BlockState state) {
        IExtendedNoiseChunk.AquifierNuke nuke = this.coo$nuke;
        if (nuke == null || state == null || !(state.is(Blocks.WATER) || state.is(Blocks.LAVA))) {
            return state;
        }
        NoiseChunk chunk = (NoiseChunk) (Object) this;
        int x = chunk.blockX();
        int y = chunk.blockY();
        int z = chunk.blockZ();
        for (BoundingBox box : nuke.boundingBoxes()) {
            int dx = x < box.minX() ? box.minX() - x : (x > box.maxX() ? x - box.maxX() : 0);
            int dy = y < box.minY() ? box.minY() - y : (y > box.maxY() ? y - box.maxY() : 0);
            int dz = z < box.minZ() ? box.minZ() - z : (z > box.maxZ() ? z - box.maxZ() : 0);
            int distance = dx + dy + dz;
            if (distance <= 5) {
                return distance <= 3 ? Blocks.CAVE_AIR.defaultBlockState() : this.coo$defaultBlock;
            }
        }
        return state;
    }

    @Override
    public IExtendedNoiseChunk.AquifierNuke irons_spellbooks$getAquifierStatus() {
        return this.coo$nuke;
    }

    @Override
    public void irons_spellbooks$setAquifierStatus(IExtendedNoiseChunk.AquifierNuke nuke) {
        this.coo$nuke = nuke;
    }
}
