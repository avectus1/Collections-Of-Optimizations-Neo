package com.misanthropy.collections_of_optimizations.mixin.elysiumapi;

import com.misanthropy.collections_of_optimizations.core.ElysiumTransformerHolder;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = Block.class, remap = false)
public abstract class MixinBlockTransformerSlot implements ElysiumTransformerHolder {
    @Unique
    private volatile Object coo$elysiumTransformer;

    @Override
    public Object coo$getElysiumTransformer() {
        return this.coo$elysiumTransformer;
    }

    @Override
    public void coo$setElysiumTransformer(Object entry) {
        this.coo$elysiumTransformer = entry;
    }
}
