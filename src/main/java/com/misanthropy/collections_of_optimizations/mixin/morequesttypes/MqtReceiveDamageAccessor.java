package com.misanthropy.collections_of_optimizations.mixin.morequesttypes;

import java.util.Map;
import java.util.UUID;
import net.pixeldreamstudios.morequesttypes.event.ReceiveDamageEventBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ReceiveDamageEventBuffer.class, remap = false)
public interface MqtReceiveDamageAccessor {
    @Accessor("BUCKETS")
    static Map<UUID, ? extends Map<Long, ?>> coo$buckets() {
        throw new AssertionError();
    }
}
