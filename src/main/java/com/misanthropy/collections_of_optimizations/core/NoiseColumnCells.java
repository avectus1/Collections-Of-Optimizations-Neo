package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.Aquifer.FluidPicker;
import net.minecraft.world.level.levelgen.DensityFunctions.BeardifierMarker;
import net.minecraft.world.level.levelgen.blending.Blender;

public final class NoiseColumnCells {
   private static final int SLOTS = 8;
   private static final ThreadLocal<NoiseColumnCells> LOCAL = ThreadLocal.withInitial(NoiseColumnCells::new);
   private final Object[] owners = new Object[8];
   private final RandomState[] states = new RandomState[8];
   private final NoiseSettings[] settings = new NoiseSettings[8];
   private final int[] cellX = new int[8];
   private final int[] cellZ = new int[8];
   private final NoiseChunk[] chunks = new NoiseChunk[8];
   private int next;

   private NoiseColumnCells() {
   }

   public static NoiseChunk get(
      Object owner, RandomState state, NoiseSettings noiseSettings, int startX, int startZ, NoiseGeneratorSettings generatorSettings, FluidPicker fluidPicker
   ) {
      NoiseColumnCells cells = LOCAL.get();

      for (int i = 0; i < 8; i++) {
         if (cells.chunks[i] != null
            && cells.owners[i] == owner
            && cells.states[i] == state
            && cells.cellX[i] == startX
            && cells.cellZ[i] == startZ
            && cells.settings[i].equals(noiseSettings)) {
            return cells.chunks[i];
         }
      }

      NoiseChunk chunk = new NoiseChunk(1, state, startX, startZ, noiseSettings, BeardifierMarker.INSTANCE, generatorSettings, fluidPicker, Blender.empty());
      chunk.initializeForFirstCellX();
      chunk.advanceCellX(0);
      int slot = cells.next;
      cells.next = (slot + 1) % 8;
      cells.owners[slot] = owner;
      cells.states[slot] = state;
      cells.settings[slot] = noiseSettings;
      cells.cellX[slot] = startX;
      cells.cellZ[slot] = startZ;
      cells.chunks[slot] = chunk;
      return chunk;
   }
}
