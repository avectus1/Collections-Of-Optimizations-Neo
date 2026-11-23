package com.misanthropy.collections_of_optimizations.mixin.morequesttypes;

import java.util.Map;
import java.util.UUID;
import net.pixeldreamstudios.morequesttypes.event.CommandEventBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = CommandEventBuffer.class, remap = false)
public interface MqtCommandAccessor {
    @Accessor("BUCKETS")
    static Map<UUID, ? extends Map<Long, ?>> coo$buckets() {
        throw new AssertionError();
    }
}
