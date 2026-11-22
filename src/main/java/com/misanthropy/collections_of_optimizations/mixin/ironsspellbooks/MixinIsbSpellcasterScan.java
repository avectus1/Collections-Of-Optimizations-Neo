package com.misanthropy.collections_of_optimizations.mixin.ironsspellbooks;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import io.redspace.ironsspellbooks.player.ClientPlayerEvents;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(
   value = {ClientPlayerEvents.class},
   remap = false
)
public abstract class MixinIsbSpellcasterScan {
   @WrapOperation(
      method = {"onPlayerTick"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/multiplayer/ClientLevel;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;",
         remap = false
      )},
      require = 0
   )
   private static List<Entity> coo$scanLoadedEntities(
      ClientLevel level, Entity except, AABB box, Predicate<? super Entity> filter, Operation<List<Entity>> original
   ) {
      if (CoOConfig.ironsspellbooksFlatSpellcasterScan && except == null) {
         List<Entity> found = new ArrayList<>();

         for (Entity entity : level.entitiesForRendering()) {
            if (entity.getBoundingBox().intersects(box) && filter.test(entity)) {
               found.add(entity);
            }
         }

         return found;
      } else {
         return (List<Entity>)original.call(new Object[]{level, except, box, filter});
      }
   }
}
