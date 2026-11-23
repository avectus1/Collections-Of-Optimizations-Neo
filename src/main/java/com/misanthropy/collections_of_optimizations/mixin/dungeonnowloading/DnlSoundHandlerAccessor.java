package com.misanthropy.collections_of_optimizations.mixin.dungeonnowloading;

import dev.hexnowloading.dungeonnowloading.sound.DNLClientSoundHandler;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = DNLClientSoundHandler.class, remap = false)
public interface DnlSoundHandlerAccessor {
    @Accessor("activeSounds")
    static Map<?, ?> coo$activeSounds() {
        throw new AssertionError();
    }
}
