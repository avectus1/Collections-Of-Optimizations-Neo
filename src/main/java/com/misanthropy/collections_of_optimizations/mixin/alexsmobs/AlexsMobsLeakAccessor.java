package com.misanthropy.collections_of_optimizations.mixin.alexsmobs;

import com.github.alexthe666.alexsmobs.event.ServerEvents;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ServerEvents.class, remap = false)
public interface AlexsMobsLeakAccessor {
    @Accessor("BEACHED_CACHALOT_WHALE_SPAWNER_MAP")
    static Map<?, ?> coo$whaleSpawners() {
        throw new AssertionError();
    }
}
