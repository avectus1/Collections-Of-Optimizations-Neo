package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import net.minecraft.world.level.levelgen.XoroshiroRandomSource.XoroshiroPositionalRandomFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = {XoroshiroPositionalRandomFactory.class}, remap = false)
public interface XoroshiroPositionalFactoryAccessor {
   @Accessor("seedLo")
   long coo$seedLo();

   @Accessor("seedHi")
   long coo$seedHi();
}
