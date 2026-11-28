package com.misanthropy.collections_of_optimizations.mixin.servercore;

import net.minecraft.server.level.ChunkHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ChunkHolder.class, remap = false)
public interface ChunkHolderChangedAccessor {
    @Accessor("hasChangedSections")
    boolean coo$hasChangedSections();
}
