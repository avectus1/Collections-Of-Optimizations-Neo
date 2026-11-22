package com.misanthropy.collections_of_optimizations.mixin.alexscaves;

import com.github.alexmodguy.alexscaves.server.level.biome.BiomeSampler;
import com.github.alexmodguy.alexscaves.server.level.biome.BiomeSampler.FloatFetcher;
import com.github.alexmodguy.alexscaves.server.level.biome.BiomeSampler.Vec3Fetcher;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   value = {BiomeSampler.class},
   remap = false
)
public abstract class MixinBiomeSamplerTickCache {
   @Unique
   private static final Holder<Biome>[] coo$holders = new Holder[216];
   @Unique
   private static BiomeManager coo$manager;
   @Unique
   private static long coo$time = Long.MIN_VALUE;
   @Unique
   private static int coo$bx;
   @Unique
   private static int coo$by;
   @Unique
   private static int coo$bz;
   @Unique
   private static boolean coo$active;

   @Unique
   private static void coo$prepare(LevelReader level, Vec3 quartPos) {
      coo$active = false;
      if (CoOConfig.alexscavesBiomeSampleTickCache && level instanceof Level real && RenderSystem.isOnRenderThread()) {
         BiomeManager manager = level.getBiomeManager();
         int bx = Mth.floor(quartPos.x) - 2;
         int by = Mth.floor(quartPos.y) - 2;
         int bz = Mth.floor(quartPos.z) - 2;
         long time = real.getGameTime();
         if (manager != coo$manager || time != coo$time || bx != coo$bx || by != coo$by || bz != coo$bz) {
            Arrays.fill(coo$holders, null);
            coo$manager = manager;
            coo$time = time;
            coo$bx = bx;
            coo$by = by;
            coo$bz = bz;
         }

         coo$active = true;
         return;
      }
   }

   @Inject(
      method = {"sampleBiomesFloatQuart"},
      at = {@At("HEAD")}
   )
   private static void coo$prepareFloat(LevelReader level, Vec3 quartPos, FloatFetcher fetcher, CallbackInfoReturnable<Float> cir) {
      coo$prepare(level, quartPos);
   }

   @Inject(
      method = {"sampleBiomesVec3Quart"},
      at = {@At("HEAD")}
   )
   private static void coo$prepareVec(LevelReader level, Vec3 quartPos, Vec3Fetcher fetcher, CallbackInfoReturnable<Vec3> cir) {
      coo$prepare(level, quartPos);
   }

   @WrapOperation(
      method = {"sampleBiomesFloatQuart", "sampleBiomesVec3Quart"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/biome/BiomeManager;getNoiseBiomeAtQuart(III)Lnet/minecraft/core/Holder;",
         remap = false
      )}
   )
   private static Holder<Biome> coo$cachedBiome(BiomeManager manager, int x, int y, int z, Operation<Holder<Biome>> original) {
      if (coo$active && manager == coo$manager) {
         int dx = x - coo$bx;
         int dy = y - coo$by;
         int dz = z - coo$bz;
         if (dx >= 0 && dy >= 0 && dz >= 0 && dx < 6 && dy < 6 && dz < 6) {
            int index = (dx * 6 + dy) * 6 + dz;
            Holder<Biome> holder = coo$holders[index];
            if (holder == null) {
               holder = (Holder<Biome>)original.call(new Object[]{manager, x, y, z});
               coo$holders[index] = holder;
            }

            return holder;
         } else {
            return (Holder<Biome>)original.call(new Object[]{manager, x, y, z});
         }
      } else {
         return (Holder<Biome>)original.call(new Object[]{manager, x, y, z});
      }
   }
}
