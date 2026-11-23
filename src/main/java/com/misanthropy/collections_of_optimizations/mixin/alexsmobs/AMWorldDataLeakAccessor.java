package com.misanthropy.collections_of_optimizations.mixin.alexsmobs;

import com.github.alexthe666.alexsmobs.world.AMWorldData;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AMWorldData.class, remap = false)
public interface AMWorldDataLeakAccessor {
    @Accessor("dataMap")
    static Map<?, ?> coo$dataMap() {
        throw new AssertionError();
    }
}
