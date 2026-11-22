package com.misanthropy.collections_of_optimizations.mixin.create;

import com.simibubi.create.content.contraptions.Contraption;
import java.util.concurrent.atomic.AtomicReference;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(
   value = {Contraption.class},
   remap = false
)
public interface ContraptionClientAccessor {
   @Accessor("clientContraption")
   AtomicReference<?> coo$clientContraption();
}
