package com.misanthropy.collections_of_optimizations.mixin.alexsmobs;

import com.github.alexthe666.alexsmobs.entity.EntityRhinoceros;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.UUID;

@Mixin(value = EntityRhinoceros.class, remap = false)
public interface RhinocerosTrustAccessor {
    @Invoker("getTrustedUUIDs")
    List<UUID> coo$getTrustedUUIDs();
}
