package com.misanthropy.collections_of_optimizations.mixin.balm;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.BlockStateKeyCache;
import net.blay09.mods.balm.common.client.rendering.AbstractCachedDynamicModel;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = AbstractCachedDynamicModel.class, remap = false)
public abstract class MixinAbstractCachedDynamicModel {
    @Redirect(method = "getQuads", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;toString()Ljava/lang/String;"), require = 0)
    private String coo$memoiseDynamicModelKey(BlockState state) {
        if (!CoOConfig.balmMemoDynamicModelKeys)
            return state.toString();
        String cached = BlockStateKeyCache.lookup(state);
        if (cached != null)
            return cached;
        String computed = state.toString();
        BlockStateKeyCache.store(state, computed);
        return computed;
    }
}
