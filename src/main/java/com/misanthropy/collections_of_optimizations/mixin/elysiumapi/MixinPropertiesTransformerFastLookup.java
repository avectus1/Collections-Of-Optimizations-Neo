package com.misanthropy.collections_of_optimizations.mixin.elysiumapi;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ElysiumTransformerHolder;
import net.jadenxgamer.elysium_api.impl.core.datadriven.block.properties_transformer.BlockPropertiesTransformer;
import net.jadenxgamer.elysium_api.impl.core.datadriven.block.properties_transformer.BlockPropertiesTransformerHelper;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Elysium asks for a block's property transformer inside very hot block state checks (is it air, does it
 * random tick, does it conduct redstone...), and each ask is a concurrent map lookup. Keep the answer on the
 * block itself, stamped with a counter that moves whenever Elysium clears its cache (datapack reload).
 */
@Mixin(value = BlockPropertiesTransformerHelper.class, remap = false)
public abstract class MixinPropertiesTransformerFastLookup {
    @Unique
    private static volatile int coo$epoch;
    @Unique
    private static final Object COO$NONE = new Object();

    @Shadow
    private static BlockPropertiesTransformer loadAndMergeTransformers(Block block) {
        throw new AssertionError();
    }

    /**
     * @author CoO
     * @reason per-block slot instead of a map lookup on every block state check
     */
    @org.spongepowered.asm.mixin.Overwrite
    public static BlockPropertiesTransformer getTransformer(Block block) {
        if (ServerLifecycleHooks.getCurrentServer() == null) {
            return null;
        }
        int epoch = coo$epoch;
        ElysiumTransformerHolder holder = (ElysiumTransformerHolder) block;
        if (CoOConfig.elysiumapiFastTransformerLookup && holder.coo$getElysiumTransformer() instanceof ElysiumTransformerHolder.Entry entry && entry.epoch() == epoch) {
            Object v = entry.value();
            return v == COO$NONE ? null : (BlockPropertiesTransformer) v;
        }
        BlockPropertiesTransformer computed = loadAndMergeTransformers(block);
        if (ServerLifecycleHooks.getCurrentServer() != null && epoch == coo$epoch) {
            holder.coo$setElysiumTransformer(new ElysiumTransformerHolder.Entry(epoch, computed == null ? COO$NONE : computed));
        }
        return computed;
    }

    @Inject(method = "invalidateCache", at = @At("HEAD"))
    private static void coo$bump(CallbackInfo ci) {
        coo$epoch++;
    }
}
