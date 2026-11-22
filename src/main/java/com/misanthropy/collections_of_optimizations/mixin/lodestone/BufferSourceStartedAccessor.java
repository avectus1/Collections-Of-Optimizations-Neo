package com.misanthropy.collections_of_optimizations.mixin.lodestone;

import com.mojang.blaze3d.vertex.BufferBuilder;
import java.util.Map;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = MultiBufferSource.BufferSource.class, remap = false)
public interface BufferSourceStartedAccessor {
    @Accessor("startedBuilders")
    Map<RenderType, BufferBuilder> coo$startedBuilders();
}
