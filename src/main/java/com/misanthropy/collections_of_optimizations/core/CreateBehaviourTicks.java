package com.misanthropy.collections_of_optimizations.core;

import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import java.lang.reflect.Method;
import java.util.function.Consumer;

public final class CreateBehaviourTicks {
   private static final ClassValue<Boolean> TICKS = new ClassValue<Boolean>() {
      protected Boolean computeValue(Class<?> type) {
         for (Class<?> current = type; current != null && current != BlockEntityBehaviour.class; current = current.getSuperclass()) {
            Method[] methods;
            try {
               methods = current.getDeclaredMethods();
            } catch (LinkageError e) {
               // A behaviour that mentions an optional mod's classes (ComputerCraft peripherals and the like)
               // cannot be inspected when that mod is missing; keep ticking it like Create does.
               return true;
            }
            for (Method method : methods) {
               if (method.getParameterCount() == 0 && (method.getName().equals("tick") || method.getName().equals("lazyTick"))) {
                  return true;
               }
            }
         }

         return false;
      }
   };
   public static final Consumer<BlockEntityBehaviour> TICK_ACTIVE = behaviour -> {
      CreateBehaviourTickFlag cached = (CreateBehaviourTickFlag)behaviour;
      byte flag = cached.coo$tickFlag();
      if (flag == 0) {
         flag = (byte)(TICKS.get(behaviour.getClass()) ? 1 : 2);
         cached.coo$setTickFlag(flag);
      }

      if (flag == 1) {
         behaviour.tick();
      }
   };

   private CreateBehaviourTicks() {
   }
}
