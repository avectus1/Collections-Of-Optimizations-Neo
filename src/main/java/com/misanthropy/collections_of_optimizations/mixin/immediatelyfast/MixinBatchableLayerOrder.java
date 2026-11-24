package com.misanthropy.collections_of_optimizations.mixin.immediatelyfast;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.client.renderer.RenderType;
import net.raphimc.immediatelyfast.feature.core.BatchableBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * endLastBatch sorts the pending layers with a comparator that re-runs getLayerOrder (texture path string
 * checks) on both sides of every comparison. Work out each layer's order once (remembered per render type)
 * and do a stable sort on that, which gives the same order Arrays.sort would.
 */
@Mixin(value = BatchableBufferSource.class, remap = false)
public abstract class MixinBatchableLayerOrder {
    @Shadow
    protected abstract int getLayerOrder(RenderType layer);

    @Unique
    private final Reference2IntOpenHashMap<RenderType> coo$layerOrders = new Reference2IntOpenHashMap<>();

    @Redirect(method = "endLastBatch", at = @At(value = "INVOKE", target = "Ljava/util/Arrays;sort([Ljava/lang/Object;Ljava/util/Comparator;)V"))
    private <T> void coo$sortByCachedOrder(T[] array, Comparator<? super T> comparator) {
        if (!CoOConfig.immediatelyfastLayerOrderCache || !(array instanceof RenderType[] layers)) {
            Arrays.sort(array, comparator);
            return;
        }
        int length = layers.length;
        long[] keys = new long[length];
        Reference2IntOpenHashMap<RenderType> orders = this.coo$layerOrders;
        if (orders.size() > 4096) {
            orders.clear();
        }
        for (int i = 0; i < length; i++) {
            RenderType layer = layers[i];
            int order;
            if (layer == null) {
                order = Integer.MAX_VALUE;
            } else if (orders.containsKey(layer)) {
                order = orders.getInt(layer);
            } else {
                order = this.getLayerOrder(layer);
                orders.put(layer, order);
            }
            // order in the high bits, original index in the low bits keeps the sort stable
            keys[i] = ((long) order << 32) | i;
        }
        Arrays.sort(keys);
        RenderType[] copy = layers.clone();
        for (int i = 0; i < length; i++) {
            layers[i] = copy[(int) (keys[i] & 0xFFFFFFFFL)];
        }
    }
}
