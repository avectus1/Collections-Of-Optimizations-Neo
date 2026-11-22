package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import java.util.Arrays;
import java.util.List;
import net.minecraft.world.level.biome.Climate.Parameter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = {"net.minecraft.world.level.biome.Climate$RTree"},
   remap = false
)
public abstract class MixinClimateRTreeSort {
   @Inject(
      method = {"sort"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private static void coo$sortByPrecomputedCenters(List<?> children, int size, int dimension, boolean absolute, CallbackInfo ci) {
      if (CoOConfig.vanillaFastBiomeTreeSort) {
         int count = children.size();
         Object[] nodes = children.toArray();
         long[][] keys = new long[count][size];

         for (int i = 0; i < count; i++) {
            Parameter[] space = ((ClimateRTreeNodeAccessor)nodes[i]).coo$parameterSpace();
            long[] key = keys[i];

            for (int d = 0; d < size; d++) {
               long center = (space[d].min() + space[d].max()) / 2L;
               key[d] = absolute ? Math.abs(center) : center;
            }
         }

         Integer[] order = new Integer[count];

         for (int i = 0; i < count; i++) {
            order[i] = i;
         }

         Arrays.sort(order, (a, b) -> {
            long[] x = keys[a];
            long[] y = keys[b];

            for (int j = 0; j < size; j++) {
               int dx = (dimension + j) % size;
               int c = Long.compare(x[dx], y[dx]);
               if (c != 0) {
                  return c;
               }
            }

            return 0;
         });
         List<Object> list = (List<Object>)children;

         for (int i = 0; i < count; i++) {
            list.set(i, nodes[order[i]]);
         }

         ci.cancel();
      }
   }
}
