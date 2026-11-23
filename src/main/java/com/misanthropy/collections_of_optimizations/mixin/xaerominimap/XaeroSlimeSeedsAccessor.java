package com.misanthropy.collections_of_optimizations.mixin.xaerominimap;

import java.util.HashMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import xaero.common.mods.SupportXaeroWorldmap;

@Mixin(value = SupportXaeroWorldmap.class, remap = false)
public interface XaeroSlimeSeedsAccessor {
    @Accessor("seedsUsed")
    static HashMap<?, Long> coo$seedsUsed() {
        throw new AssertionError();
    }
}
