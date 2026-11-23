package com.misanthropy.collections_of_optimizations.mixin.morequesttypes;

import java.util.Map;
import java.util.UUID;
import net.pixeldreamstudios.morequesttypes.event.UseItemEventBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = UseItemEventBuffer.class, remap = false)
public interface MqtUseItemAccessor {
    @Accessor("BUCKETS")
    static Map<UUID, ? extends Map<Long, ?>> coo$buckets() {
        throw new AssertionError();
    }

    @Accessor("SEEN")
    static Map<UUID, ? extends Map<Long, ?>> coo$seen() {
        throw new AssertionError();
    }
}
