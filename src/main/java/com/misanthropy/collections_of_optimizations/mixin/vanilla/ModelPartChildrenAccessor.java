package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import java.util.Map;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ModelPart.class, remap = false)
public interface ModelPartChildrenAccessor {
    @Accessor("children")
    Map<String, ModelPart> coo$children();
}
