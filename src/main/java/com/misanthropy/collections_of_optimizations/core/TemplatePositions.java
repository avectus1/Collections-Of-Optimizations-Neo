package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.core.BlockPos;

public final class TemplatePositions {
   private static final int BITS = 7;
   private static final int SIZE = 128;
   private static final BlockPos[] TABLE = new BlockPos[2097152];

   private TemplatePositions() {
   }

   public static BlockPos of(int x, int y, int z) {
      if ((x | y | z) >= 0 && x < 128 && y < 128 && z < 128) {
         int index = y << 14 | z << 7 | x;
         BlockPos pos = TABLE[index];
         if (pos == null) {
            pos = new BlockPos(x, y, z);
            TABLE[index] = pos;
         }

         return pos;
      } else {
         return new BlockPos(x, y, z);
      }
   }
}
