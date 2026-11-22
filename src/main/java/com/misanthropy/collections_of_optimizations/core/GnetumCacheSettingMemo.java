package com.misanthropy.collections_of_optimizations.core;

import it.unimi.dsi.fastutil.objects.Object2ByteOpenHashMap;

public final class GnetumCacheSettingMemo {
   public static final byte UNKNOWN = -1;
   private static final int SLOTS = 4;
   private static final Object2ByteOpenHashMap<String>[] MEMO = newSlots();
   private static int stamp = Integer.MIN_VALUE;

   private GnetumCacheSettingMemo() {
   }

   private static Object2ByteOpenHashMap<String>[] newSlots() {
      Object2ByteOpenHashMap<String>[] slots = new Object2ByteOpenHashMap[4];

      for (int i = 0; i < 4; i++) {
         slots[i] = new Object2ByteOpenHashMap(32);
         slots[i].defaultReturnValue((byte)-1);
      }

      return slots;
   }

   public static byte get(int slot, String id, int tick) {
      if (tick == stamp) {
         return MEMO[slot].getByte(id);
      } else {
         stamp = tick;

         for (int i = 0; i < 4; i++) {
            MEMO[i].clear();
         }

         return -1;
      }
   }

   public static void record(int slot, String id, boolean disabled) {
      MEMO[slot].put(id, (byte)(disabled ? 1 : 0));
   }
}
