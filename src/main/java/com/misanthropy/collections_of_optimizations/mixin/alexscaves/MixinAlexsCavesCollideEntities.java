package com.misanthropy.collections_of_optimizations.mixin.alexscaves;

import com.bawnorton.mixinsquared.TargetHandler;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.RadiumEntityCollisions;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

@Mixin(value = {Entity.class},
   priority = 1500,
   remap = false
)
public abstract class MixinAlexsCavesCollideEntities {
   @TargetHandler(
      mixin = "com.github.alexmodguy.alexscaves.mixin.EntityMixin",
      name = "ac_collide"
   )
   @WrapOperation(
      method = {"@MixinSquared:Handler"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/Level;getEntityCollisions(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"
      ),
      require = 0,
      remap = false
   )
   private List<VoxelShape> coo$radiumEntityCollisions(Level level, Entity entity, AABB box, Operation<List<VoxelShape>> original) {
      return CoOConfig.alexscavesRadiumEntityCollisions ? RadiumEntityCollisions.collisions(level, entity, box) : original.call(level, entity, box);
   }
}
