package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.world.entity.ai.goal.Goal;

public final class ThrottledCanUseGoal extends Goal {
   private final Goal delegate;
   private final int interval;
   private int cooldown;

   public ThrottledCanUseGoal(Goal delegate, int interval) {
      this.delegate = delegate;
      this.interval = Math.max(1, interval);
      this.setFlags(delegate.getFlags());
   }

   public boolean canUse() {
      if (this.cooldown > 0) {
         this.cooldown--;
         return false;
      } else if (this.delegate.canUse()) {
         return true;
      } else {
         this.cooldown = this.interval;
         return false;
      }
   }

   public boolean canContinueToUse() {
      return this.delegate.canContinueToUse();
   }

   public boolean isInterruptable() {
      return this.delegate.isInterruptable();
   }

   public void start() {
      this.cooldown = 0;
      this.delegate.start();
   }

   public void stop() {
      this.cooldown = 0;
      this.delegate.stop();
   }

   public boolean requiresUpdateEveryTick() {
      return this.delegate.requiresUpdateEveryTick();
   }

   public void tick() {
      this.delegate.tick();
   }

   public String toString() {
      return this.delegate.toString();
   }
}
