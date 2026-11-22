package com.misanthropy.collections_of_optimizations.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SplittableRandom;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.TargetPoint;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class FlatClimateTree {
   private static final Logger LOGGER = LogManager.getLogger("collections_of_optimizations");
   private static final int PARAMETERS = 7;
   private static final int CHECKED_TARGETS = 512;
   private static final ThreadLocal<long[]> SCRATCH = ThreadLocal.withInitial(() -> new long[8]);
   public static final FlatClimateTree UNUSABLE = new FlatClimateTree();
   private static final Map<Class<?>, Boolean> METRICS = new ConcurrentHashMap<>();
   private static volatile boolean checking;
   private static Class<?> leafClass;
   private static Field rootField;
   private static Field lastField;
   private static Field childrenField;
   private static Field valueField;
   private static Field spaceField;
   private static Method metricMethod;
   private static Method searchMethod;
   private static boolean reflectionReady;
   private static boolean reflectionBroken;
   private final long[] mins;
   private final long[] maxs;
   private final int[] firstChild;
   private final int[] childCount;
   private final Object[] nodes;
   private final Object[] values;
   private final ThreadLocal<Object> last;

   private FlatClimateTree() {
      this.mins = this.maxs = null;
      this.firstChild = this.childCount = null;
      this.nodes = this.values = null;
      this.last = null;
   }

   private FlatClimateTree(List<Object> order, int[] firstChild, int[] childCount, ThreadLocal<Object> last) throws IllegalAccessException {
      int n = order.size();
      this.mins = new long[n * 7];
      this.maxs = new long[n * 7];
      this.firstChild = firstChild;
      this.childCount = childCount;
      this.nodes = order.toArray();
      this.values = new Object[n];
      this.last = last;

      for (int i = 0; i < n; i++) {
         Object node = this.nodes[i];
         Parameter[] space = (Parameter[])spaceField.get(node);

         for (int k = 0; k < 7; k++) {
            this.mins[i * 7 + k] = space[k].min();
            this.maxs[i * 7 + k] = space[k].max();
         }

         if (childCount[i] == 0) {
            this.values[i] = valueField.get(node);
            ((FlatClimateNode)node).coo$setFlatIndex(i);
         }
      }
   }

   public static boolean checking() {
      return checking;
   }

   public static FlatClimateTree build(Object tree, Object metric) {
      try {
         if (!reflection()) {
            return UNUSABLE;
         } else {
            Function<TargetPoint, Object> vanillaSearch = target -> {
               try {
                  return searchMethod.invoke(tree, target, metric);
               } catch (ReflectiveOperationException var4x) {
                  throw new IllegalStateException(var4x);
               }
            };
            Object root = rootField.get(tree);
            ThreadLocal<Object> last = (ThreadLocal<Object>)lastField.get(tree);
            List<Object> order = new ArrayList<>();
            order.add(root);
            List<int[]> ranges = new ArrayList<>();

            for (int i = 0; i < order.size(); i++) {
               Object node = order.get(i);
               if (leafClass.isInstance(node)) {
                  ranges.add(new int[]{0, 0});
               } else {
                  Object[] children = (Object[])childrenField.get(node);
                  ranges.add(new int[]{order.size(), children.length});

                  for (Object child : children) {
                     order.add(child);
                  }
               }
            }

            int[] first = new int[order.size()];
            int[] count = new int[order.size()];

            for (int ix = 0; ix < first.length; ix++) {
               first[ix] = ranges.get(ix)[0];
               count[ix] = ranges.get(ix)[1];
            }

            FlatClimateTree flat = new FlatClimateTree(order, first, count, last);
            if (!flat.matchesVanilla(vanillaSearch)) {
               LOGGER.warn("Flat biome search disagreed with the vanilla search on a {} entry biome tree, that tree keeps the vanilla search", order.size());
               return UNUSABLE;
            } else {
               return flat;
            }
         }
      } catch (Throwable var14) {
         LOGGER.warn("Flat biome search is off for one biome tree", var14);
         return UNUSABLE;
      }
   }

   public boolean usable() {
      return this != UNUSABLE;
   }

   public boolean acceptsMetric(Object metric) {
      Class<?> type = metric.getClass();
      Boolean known = METRICS.get(type);
      if (known == null) {
         known = this.sameAsNodeDistance(metric);
         METRICS.put(type, known);
         if (!known) {
            LOGGER.info("Biome tree searches through {} keep the vanilla search", type.getName());
         }
      }

      return known;
   }

   public Object search(TargetPoint target) {
      long[] t = SCRATCH.get();
      t[0] = target.temperature();
      t[1] = target.humidity();
      t[2] = target.continentalness();
      t[3] = target.erosion();
      t[4] = target.depth();
      t[5] = target.weirdness();
      t[6] = 0L;
      Object previous = this.last.get();
      int start = -1;
      long startDistance = Long.MAX_VALUE;
      if (previous instanceof FlatClimateNode indexed) {
         start = indexed.coo$flatIndex();
         if (start < 0 || start >= this.nodes.length || this.nodes[start] != previous) {
            return null;
         }

         startDistance = this.distance(start, t);
      } else if (previous != null) {
         return null;
      }

      int leaf = this.childCount[0] == 0 ? 0 : this.search(0, t, start, startDistance);
      if (this.nodes[leaf] != previous) {
         this.last.set(this.nodes[leaf]);
      }

      return this.values[leaf];
   }

   private static long[] parameters(TargetPoint t) {
      return new long[]{t.temperature(), t.humidity(), t.continentalness(), t.erosion(), t.depth(), t.weirdness(), 0L};
   }

   private int search(int node, long[] t, int best, long bestDistance) {
      long i = bestDistance;
      int leaf = best;
      int end = this.firstChild[node] + this.childCount[node];

      for (int child = this.firstChild[node]; child < end; child++) {
         long j = this.distance(child, t);
         if (i > j) {
            int found;
            long k;
            if (this.childCount[child] == 0) {
               found = child;
               k = j;
            } else {
               found = this.search(child, t, leaf, i);
               k = t[7];
            }

            if (i > k) {
               i = k;
               leaf = found;
            }
         }
      }

      t[7] = i;
      return leaf;
   }

   private long distance(int node, long[] t) {
      int base = node * 7;
      long sum = 0L;

      for (int k = 0; k < 7; k++) {
         long p = t[k];
         long above = p - this.maxs[base + k];
         long below = this.mins[base + k] - p;
         long d = above > 0L ? above : Math.max(below, 0L);
         sum += d * d;
      }

      return sum;
   }

   private boolean matchesVanilla(Function<TargetPoint, Object> vanillaSearch) {
      SplittableRandom random = new SplittableRandom(24301L ^ (long)this.nodes.length);
      Object saved = this.last.get();
      checking = true;

      try {
         for (int n = 0; n < 512; n++) {
            TargetPoint target = this.randomTarget(random, n);
            this.last.set(null);
            Object expected = vanillaSearch.apply(target);
            Object expectedLeaf = this.last.get();
            this.last.set(null);
            Object actual = this.search(target);
            if (actual != expected || this.last.get() != expectedLeaf) {
               return false;
            }

            int warmIndex = random.nextInt(this.nodes.length);
            Object warmStart = this.nodes[warmIndex];
            if (this.childCount[warmIndex] == 0) {
               TargetPoint next = this.randomTarget(random, n + 1);
               this.last.set(warmStart);
               expected = vanillaSearch.apply(next);
               expectedLeaf = this.last.get();
               this.last.set(warmStart);
               actual = this.search(next);
               if (actual != expected || this.last.get() != expectedLeaf) {
                  return false;
               }
            }
         }

         return true;
      } finally {
         checking = false;
         this.last.set(saved);
      }
   }

   private TargetPoint randomTarget(SplittableRandom random, int n) {
      if ((n & 1) == 0) {
         int leaf = random.nextInt(this.nodes.length);
         int base = leaf * 7;
         long[] p = new long[7];

         for (int k = 0; k < 6; k++) {
            long lo = this.mins[base + k];
            long hi = this.maxs[base + k];
            p[k] = hi > lo ? lo + random.nextLong(hi - lo + 1L) : lo;
         }

         return new TargetPoint(p[0], p[1], p[2], p[3], p[4], p[5]);
      } else {
         return new TargetPoint(
            random.nextLong(-22000L, 22001L),
            random.nextLong(-22000L, 22001L),
            random.nextLong(-22000L, 22001L),
            random.nextLong(-22000L, 22001L),
            random.nextLong(-22000L, 22001L),
            random.nextLong(-22000L, 22001L)
         );
      }
   }

   private boolean sameAsNodeDistance(Object metric) {
      try {
         SplittableRandom random = new SplittableRandom((long)this.nodes.length);

         for (int n = 0; n < 4; n++) {
            long[] t = parameters(this.randomTarget(random, n));

            for (int i = 0; i < this.nodes.length; i++) {
               long expected = this.distance(i, t);
               long actual = (Long)metricMethod.invoke(metric, this.nodes[i], t);
               if (actual != expected) {
                  return false;
               }
            }
         }

         return true;
      } catch (Throwable var10) {
         return false;
      }
   }

   private static synchronized boolean reflection() {
      if (reflectionReady) {
         return true;
      } else if (reflectionBroken) {
         return false;
      } else {
         try {
            Class<?> tree = Class.forName("net.minecraft.world.level.biome.Climate$RTree");
            Class<?> node = Class.forName("net.minecraft.world.level.biome.Climate$RTree$Node");
            Class<?> subTree = Class.forName("net.minecraft.world.level.biome.Climate$RTree$SubTree");
            leafClass = Class.forName("net.minecraft.world.level.biome.Climate$RTree$Leaf");
            rootField = ObfuscationReflectionHelper.findField(tree, "root");
            lastField = ObfuscationReflectionHelper.findField(tree, "lastResult");
            childrenField = ObfuscationReflectionHelper.findField(subTree, "children");
            valueField = ObfuscationReflectionHelper.findField(leafClass, "value");
            spaceField = ObfuscationReflectionHelper.findField(node, "parameterSpace");
            Class<?> metric = Class.forName("net.minecraft.world.level.biome.Climate$DistanceMetric");

            for (Method m : metric.getDeclaredMethods()) {
               if (Modifier.isAbstract(m.getModifiers())) {
                  metricMethod = m;
               }
            }

            metricMethod.setAccessible(true);
            searchMethod = ObfuscationReflectionHelper.findMethod(tree, "search", new Class[]{TargetPoint.class, metric});
            reflectionReady = true;
            return true;
         } catch (Throwable var8) {
            reflectionBroken = true;
            LOGGER.warn("Flat biome search is off, the biome tree did not look as expected", var8);
            return false;
         }
      }
   }
}
