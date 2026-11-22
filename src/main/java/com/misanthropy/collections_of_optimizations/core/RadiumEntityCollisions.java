package com.misanthropy.collections_of_optimizations.core;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class RadiumEntityCollisions {
   private RadiumEntityCollisions() {
   }

   public static List<VoxelShape> collisions(Level level, Entity entity, AABB box) {
      MethodHandle handle = RadiumEntityCollisions.Holder.HANDLE;
      if (handle != null) {
         try {
            return (List)handle.invokeExact((Level)level, (Entity)entity, (AABB)box, (boolean)false);
         } catch (Throwable var5) {
         }
      }

      return level.getEntityCollisions(entity, box);
   }

   private static final class Holder {
      static final MethodHandle HANDLE = find();

      private static MethodHandle find() {
         try {
            Class<?> type = Class.forName(
               "me.jellysquid.mods.lithium.common.entity.LithiumEntityCollisions", true, RadiumEntityCollisions.class.getClassLoader()
            );
            return MethodHandles.publicLookup()
               .findStatic(type, "getEntityWorldBorderCollisions", MethodType.methodType(List.class, Level.class, Entity.class, AABB.class, boolean.class));
         } catch (Throwable var1) {
            return null;
         }
      }
   }
}
