package com.misanthropy.collections_of_optimizations.core;

import java.lang.reflect.Field;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public final class DummyDamageNumbers {
   private static final int UNRESOLVED = 0;
   private static final int READY = 1;
   private static final int FAILED = 2;
   private static final ResourceLocation TRUE_DAMAGE = ResourceLocation.fromNamespaceAndPath("dummmmmmy", "true");
   private static volatile int state = 0;
   private static Supplier<?> damageMode;
   private static Supplier<?> healMode;
   private static Object noneMode;
   private static Object allPlayersMode;
   private static Object localPlayerMode;

   private DummyDamageNumbers() {
   }

   public static ResourceLocation trueDamage() {
      return TRUE_DAMAGE;
   }

   public static boolean skipDamageNumber(boolean attackerIsServerPlayer) {
      if (!resolve()) {
         return false;
      } else {
         Object mode;
         try {
            mode = damageMode.get();
         } catch (Throwable var3) {
            return false;
         }

         return unusedMode(mode, attackerIsServerPlayer);
      }
   }

   public static boolean skipHealNumber(boolean targetIsServerPlayer) {
      if (!resolve()) {
         return false;
      } else {
         Object mode;
         try {
            mode = healMode.get();
         } catch (Throwable var3) {
            return false;
         }

         return unusedMode(mode, targetIsServerPlayer);
      }
   }

   private static boolean unusedMode(Object mode, boolean hasServerPlayer) {
      if (mode == null) {
         return false;
      } else {
         return mode == noneMode ? true : !hasServerPlayer && (mode == allPlayersMode || mode == localPlayerMode);
      }
   }

   private static boolean resolve() {
      int current = state;
      if (current == 1) {
         return true;
      } else {
         return current == 2 ? false : resolveSlow();
      }
   }

   private static synchronized boolean resolveSlow() {
      if (state == READY) {
         return true;
      }
      if (state == FAILED) {
         return false;
      }
      try {
         ClassLoader loader = DummyDamageNumbers.class.getClassLoader();
         Class<?> configs = Class.forName("net.mehvahdjukaar.dummmmmmy.configs.CommonConfigs", true, loader);
         Class<?> modeType = Class.forName("net.mehvahdjukaar.dummmmmmy.configs.CommonConfigs$Mode", true, loader);
         Field damageField = configs.getField("DAMAGE_NUMBERS_MODE");
         Field healField = configs.getField("HEALING_NUMBERS_MODE");
         Object damage = damageField.get(null);
         Object heal = healField.get(null);
         Object none = null;
         Object all = null;
         Object local = null;
         for (Object constant : modeType.getEnumConstants()) {
            String name = ((Enum<?>) constant).name();
            if (name.equals("NONE")) {
               none = constant;
            } else if (name.equals("ALL_PLAYERS")) {
               all = constant;
            } else if (name.equals("LOCAL_PLAYER")) {
               local = constant;
            }
         }
         if (!(damage instanceof Supplier) || !(heal instanceof Supplier) || none == null) {
            state = FAILED;
            return false;
         }
         damageMode = (Supplier<?>) damage;
         healMode = (Supplier<?>) heal;
         noneMode = none;
         allPlayersMode = all;
         localPlayerMode = local;
         state = READY;
         return true;
      } catch (Throwable t) {
         state = FAILED;
         return false;
      }
   }
}
