package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ParameterListEncodeCache<T> implements Codec<ParameterList<T>> {
   private static final Logger LOGGER = LogManager.getLogger("collections_of_optimizations");
   private static final int VERIFIED_REUSES = 2;
   private static volatile boolean broken;
   private final Codec<ParameterList<T>> delegate;
   private final Map<ParameterList<T>, ParameterListEncodeCache.Encoded> encoded = Collections.synchronizedMap(new WeakHashMap<>());

   private ParameterListEncodeCache(Codec<ParameterList<T>> delegate) {
      this.delegate = delegate;
   }

   public static <T> Codec<ParameterList<T>> wrap(Codec<ParameterList<T>> delegate) {
      return new ParameterListEncodeCache<>(delegate);
   }

   public <R> DataResult<Pair<ParameterList<T>, R>> decode(DynamicOps<R> ops, R input) {
      return this.delegate.decode(ops, input);
   }

   public <R> DataResult<R> encode(ParameterList<T> input, DynamicOps<R> ops, R prefix) {
      if (!broken
         && CoOConfig.vanillaReuseBiomeListEncoding
         && input != null
         && prefix == ops.empty()
         && prefix instanceof EndTag
         && ops instanceof RegistryOps<R> registryOps) {
         Object owner = registryOps.owner(Registries.BIOME).orElse(null);
         if (owner == null) {
            return this.delegate.encode(input, ops, prefix);
         } else {
            List<Pair<ParameterPoint, T>> values = input.values();
            ParameterListEncodeCache.Encoded known = this.encoded.get(input);
            if (known == null || known.owner != owner || !same(values, known.entries)) {
               DataResult<R> fresh = this.delegate.encode(input, ops, prefix);
               if (fresh.error().isEmpty() && fresh.result().isPresent() && fresh.result().get() instanceof Tag tag) {
                  this.encoded.put(input, new ParameterListEncodeCache.Encoded(owner, values.toArray(), copyInOrder(tag)));
               }

               return fresh;
            } else if (known.reuses < 2) {
               DataResult<R> fresh = this.delegate.encode(input, ops, prefix);
               if (!fresh.result().isEmpty() && known.tag.equals(fresh.result().get())) {
                  known.reuses++;
                  return fresh;
               } else {
                  broken = true;
                  LOGGER.warn("A reused biome list encoding differed from a fresh one!! Biome lists are encoded fresh from now on.");
                  return fresh;
               }
            } else {
               @SuppressWarnings("unchecked") R reused = (R) copyInOrder(known.tag);
               return DataResult.success(reused);
            }
         }
      } else {
         return this.delegate.encode(input, ops, prefix);
      }
   }

   private static Tag copyInOrder(Tag tag) {
      if (tag instanceof CompoundTag compound) {
         CompoundTag copy = new CompoundTag();

         for (String key : compound.getAllKeys()) {
            copy.put(key, copyInOrder(compound.get(key)));
         }

         return copy;
      } else if (!(tag instanceof ListTag list)) {
         return tag.copy();
      } else {
         ListTag copy = new ListTag();

         for (Tag element : list) {
            copy.add(copyInOrder(element));
         }

         return copy;
      }
   }

   private static boolean same(List<?> values, Object[] entries) {
      if (values.size() != entries.length) {
         return false;
      } else {
         for (int i = 0; i < entries.length; i++) {
            if (values.get(i) != entries[i]) {
               return false;
            }
         }

         return true;
      }
   }

   @Override
   public String toString() {
      return this.delegate.toString();
   }

   private static final class Encoded {
      final Object owner;
      final Object[] entries;
      final Tag tag;
      int reuses;

      Encoded(Object owner, Object[] entries, Tag tag) {
         this.owner = owner;
         this.entries = entries;
         this.tag = tag;
      }
   }
}
