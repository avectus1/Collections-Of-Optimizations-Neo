package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.core.BlockPos;

/**
 * Shared BlockPos instances for structure template coordinates below 128 on every axis. Layers are
 * allocated by height the first time a template uses that height, instead of one 2 million slot table
 * (8 MB) up front, since most templates are only a few dozen blocks tall.
 */
public final class TemplatePositions {
   private static final int SIZE = 128;
   private static final BlockPos[][] LAYERS = new BlockPos[SIZE][];

   private TemplatePositions() {
   }

   public static BlockPos of(int x, int y, int z) {
      if ((x | y | z) >= 0 && x < SIZE && y < SIZE && z < SIZE) {
         BlockPos[] layer = LAYERS[y];
         if (layer == null) {
            // A race here only means two threads build their own layer and one is dropped; every position
            // handed out is still a correct, immutable BlockPos.
            layer = new BlockPos[SIZE * SIZE];
            LAYERS[y] = layer;
         }
         int index = z << 7 | x;
         BlockPos pos = layer[index];
         if (pos == null) {
            pos = new BlockPos(x, y, z);
            layer[index] = pos;
         }
         return pos;
      } else {
         return new BlockPos(x, y, z);
      }
   }
}
