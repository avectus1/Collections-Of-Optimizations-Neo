package com.misanthropy.collections_of_optimizations.mixin.sophisticatedbackpacks;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlock;
import net.p3pp3rf1y.sophisticatedbackpacks.client.render.ClientBackpackShapeProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   value = {ClientBackpackShapeProvider.class},
   remap = false
)
public abstract class MixinClientBackpackShapeProviderLazy {
   @Shadow
   private volatile Map<BlockState, VoxelShape> shapeCache;
   @Unique
   private volatile Map<BlockState, VoxelShape> coo$lazyShapes;

   @Shadow
   private VoxelShape computeShapeFromLoadedModel(Minecraft minecraft, BlockState state) {
      throw new AssertionError();
   }

   @Inject(
      method = {"rebuildShapes"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void coo$deferShapes(CallbackInfo ci) {
      if (CoOConfig.sophisticatedbackpacksLazyShapes && Minecraft.getInstance().getBlockRenderer() != null) {
         this.shapeCache = Map.of();
         this.coo$lazyShapes = new ConcurrentHashMap<>();
         ci.cancel();
      } else {
         this.coo$lazyShapes = null;
      }
   }

   @Inject(
      method = {"getShape"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void coo$shapeOnDemand(BlockState state, CallbackInfoReturnable<VoxelShape> cir) {
      Map<BlockState, VoxelShape> lazy = this.coo$lazyShapes;
      if (lazy != null && state.getBlock() instanceof BackpackBlock) {
         VoxelShape shape = lazy.get(state);
         if (shape == null) {
            shape = this.computeShapeFromLoadedModel(Minecraft.getInstance(), state);
            lazy.put(state, shape);
         }

         cir.setReturnValue(shape);
      }
   }
}
