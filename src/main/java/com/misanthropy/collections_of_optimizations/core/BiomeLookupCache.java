package com.misanthropy.collections_of_optimizations.core;

import it.unimi.dsi.fastutil.HashCommon;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.Sampler;

public final class BiomeLookupCache {
   private static final int BITS = 15;
   private static final int MASK = 32767;
   private static final int XZ_LIMIT = 8388608;
   private static final int Y_LIMIT = 2048;
   private final ThreadLocal<BiomeLookupCache.Table> tables = ThreadLocal.withInitial(BiomeLookupCache.Table::new);

   public static boolean cacheable(int x, int y, int z) {
      return x > -8388608 && x < 8388608 && z > -8388608 && z < 8388608 && y > -2048 && y < 2048;
   }

   private static long key(int x, int y, int z) {
      return (long)(x & 16777215) << 36 | (long)(y & 4095) << 24 | (long)(z & 16777215);
   }

   public Holder<Biome> get(Sampler sampler, int x, int y, int z) {
      long key = key(x, y, z);
      int slot = (int)HashCommon.mix(key) & 32767;
      BiomeLookupCache.Table t = this.tables.get();
      return t.keys[slot] == key && t.samplers[slot] == sampler ? (Holder)t.values[slot] : null;
   }

   public void put(Sampler sampler, int x, int y, int z, Holder<Biome> value) {
      long key = key(x, y, z);
      int slot = (int)HashCommon.mix(key) & 32767;
      BiomeLookupCache.Table t = this.tables.get();
      t.keys[slot] = key;
      t.samplers[slot] = sampler;
      t.values[slot] = value;
   }

   private static final class Table {
      final long[] keys = new long[32768];
      final Object[] samplers = new Object[32768];
      final Object[] values = new Object[32768];
   }
}
