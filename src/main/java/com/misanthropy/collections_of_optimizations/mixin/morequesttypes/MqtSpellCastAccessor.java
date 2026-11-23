package com.misanthropy.collections_of_optimizations.mixin.morequesttypes;

import java.util.Map;
import java.util.UUID;
import net.pixeldreamstudios.morequesttypes.event.SpellCastEventBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = SpellCastEventBuffer.class, remap = false)
public interface MqtSpellCastAccessor {
    @Accessor("BUCKETS")
    static Map<UUID, ? extends Map<Long, ?>> coo$buckets() {
        throw new AssertionError();
    }
}
