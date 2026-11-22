package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = {Entity.class},
   priority = 2000,
   remap = false
)
public abstract class MixinEntityClientFireImmuneMemo {
   @Unique
   private long coo$fireImmuneTime = Long.MIN_VALUE;
   @Unique
   private boolean coo$fireImmune;

   @Shadow
   public abstract Level level();

   @WrapMethod(
      method = {"fireImmune"}
   )
   private boolean coo$memoClientFireImmune(Operation<Boolean> original) {
      Level level = this.level();
      if (level != null && level.isClientSide && CoOConfig.vanillaClientFireImmuneMemo) {
         long now = level.getGameTime();
         if (now == this.coo$fireImmuneTime) {
            return this.coo$fireImmune;
         } else {
            boolean immune = (Boolean)original.call(new Object[0]);
            this.coo$fireImmune = immune;
            this.coo$fireImmuneTime = now;
            return immune;
         }
      } else {
         return (Boolean)original.call(new Object[0]);
      }
   }
}
