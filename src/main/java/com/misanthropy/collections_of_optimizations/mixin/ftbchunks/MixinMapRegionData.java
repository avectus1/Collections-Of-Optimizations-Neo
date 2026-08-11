package com.misanthropy.collections_of_optimizations.mixin.ftbchunks;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import dev.ftb.mods.ftbchunks.FTBChunks;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.client.map.MapChunk;
import dev.ftb.mods.ftbchunks.client.map.MapRegion;
import dev.ftb.mods.ftbchunks.client.map.MapRegionData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Mixin(value = MapRegionData.class, remap = false)
public abstract class MixinMapRegionData {
    @Shadow
    @Final
    public MapRegion region;

    @Shadow
    @Final
    public short[] height;

    @Shadow
    @Final
    public short[] waterLightAndBiome;

    @Shadow
    @Final
    public int[] foliage;

    @Shadow
    @Final
    public int[] grass;

    @Shadow
    @Final
    public int[] water;

    @Invoker("writeData")
    abstract void coo$writeData(Collection<MapChunk> chunkList, Map<String, BufferedImage> images) throws Exception;

    @Inject(method = "write()V", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$fastRegionWrite(CallbackInfo ci) {
        if (!CoOConfig.ftbchunksFastRegionWrite) {
            return;
        }

        Collection<MapChunk> chunkList;
        synchronized (this.region.dimension.getManager().lock) {
            chunkList = this.region.getModifiedChunks();
        }
        ci.cancel();
        if (chunkList.isEmpty()) {
            return;
        }

        short[] heightCopy = Arrays.copyOf(this.height, this.height.length);
        short[] waterLightAndBiomeCopy = Arrays.copyOf(this.waterLightAndBiome, this.waterLightAndBiome.length);
        int[] foliageCopy = Arrays.copyOf(this.foliage, this.foliage.length);
        int[] grassCopy = Arrays.copyOf(this.grass, this.grass.length);
        int[] waterCopy = Arrays.copyOf(this.water, this.water.length);

        FTBChunksClient.MAP_EXECUTOR.execute(() -> coo$writeRegion(
                chunkList, heightCopy, waterLightAndBiomeCopy, foliageCopy, grassCopy, waterCopy));
    }

    private void coo$writeRegion(Collection<MapChunk> chunkList, short[] heightCopy, short[] waterLightAndBiomeCopy,
                                 int[] foliageCopy, int[] grassCopy, int[] waterCopy) {
        try {
            BufferedImage dataImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
            BufferedImage foliageImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
            BufferedImage grassImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
            BufferedImage waterImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
            BufferedImage blocksImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);

            int[] dataPixels = ((DataBufferInt) dataImage.getRaster().getDataBuffer()).getData();
            int[] foliagePixels = ((DataBufferInt) foliageImage.getRaster().getDataBuffer()).getData();
            int[] grassPixels = ((DataBufferInt) grassImage.getRaster().getDataBuffer()).getData();
            int[] waterPixels = ((DataBufferInt) waterImage.getRaster().getDataBuffer()).getData();
            int[] blocksPixels = ((DataBufferInt) blocksImage.getRaster().getDataBuffer()).getData();

            for (int index = 0; index < dataPixels.length; index++) {
                dataPixels[index] = ((heightCopy[index] & 0xFFFF) << 16) | (waterLightAndBiomeCopy[index] & 0xFFFF);
                foliagePixels[index] = foliageCopy[index] & 0xFFFFFF;
                grassPixels[index] = grassCopy[index] & 0xFFFFFF;
                waterPixels[index] = waterCopy[index] & 0xFFFFFF;
                blocksPixels[index] = ((foliageCopy[index] >>> 24) & 0xFF) << 16
                        | ((grassCopy[index] >>> 24) & 0xFF) << 8
                        | ((waterCopy[index] >>> 24) & 0xFF);
            }

            this.coo$writeData(chunkList, Map.of(
                    "data.png", dataImage,
                    "foliage.png", foliageImage,
                    "grass.png", grassImage,
                    "water.png", waterImage,
                    "blocks.png", blocksImage
            ));
        } catch (Exception exception) {
            FTBChunks.LOGGER.error("Failed to write map region " + this.region.dimension + ":" + this.region + ":", exception);
        }
    }
}
