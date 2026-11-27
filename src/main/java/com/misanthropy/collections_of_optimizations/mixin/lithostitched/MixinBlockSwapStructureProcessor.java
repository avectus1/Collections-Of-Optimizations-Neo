package com.misanthropy.collections_of_optimizations.mixin.lithostitched;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import dev.worldgen.lithostitched.impl.worldgen.processor.BlockSwapStructureProcessor;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   value = {BlockSwapStructureProcessor.class},
   remap = false
)
public abstract class MixinBlockSwapStructureProcessor {
   @Shadow
   @Final
   private Map<ResourceKey<Block>, ResourceKey<Block>> blockSwapMap;
   @Unique
   private volatile Map<Block, Block> coo$resolvedSwaps;

   @Inject(
      method = {"processBlock"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void coo$fastBlockSwap(
      LevelReader level,
      BlockPos pos,
      BlockPos pivot,
      StructureBlockInfo relative,
      StructureBlockInfo current,
      StructurePlaceSettings settings,
      CallbackInfoReturnable<StructureBlockInfo> cir
   ) {
      if (CoOConfig.lithostitchedCacheBlockSwapMap) {
         Map<Block, Block> resolved = this.coo$resolvedSwaps;
         if (resolved == null) {
            resolved = new IdentityHashMap<>(this.blockSwapMap.size());

            for (Entry<ResourceKey<Block>, ResourceKey<Block>> swap : this.blockSwapMap.entrySet()) {
               Block from = BuiltInRegistries.BLOCK.getOptional(swap.getKey()).orElse(null);
               Block to = BuiltInRegistries.BLOCK.getOptional(swap.getValue()).orElse(null);
               if (from != null && to != null) {
                  resolved.put(from, to);
               }
            }

            this.coo$resolvedSwaps = resolved;
         }

         Block replacement = resolved.get(current.state().getBlock());
         cir.setReturnValue(
            replacement == null ? current : new StructureBlockInfo(current.pos(), replacement.withPropertiesOf(current.state()), current.nbt())
         );
      }
   }
}
