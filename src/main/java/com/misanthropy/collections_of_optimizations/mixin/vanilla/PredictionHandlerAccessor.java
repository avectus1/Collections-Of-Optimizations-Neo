package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BlockStatePredictionHandler.class, remap = false)
public interface PredictionHandlerAccessor {
    @Accessor("serverVerifiedStates")
    Long2ObjectOpenHashMap<?> coo$serverVerifiedStates();
}
