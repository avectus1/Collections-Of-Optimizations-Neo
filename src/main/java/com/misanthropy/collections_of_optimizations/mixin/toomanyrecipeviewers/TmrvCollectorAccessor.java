package com.misanthropy.collections_of_optimizations.mixin.toomanyrecipeviewers;

import dev.nolij.toomanyrecipeviewers.impl.jei.api.gui.builder.TMRVIngredientCollector;
import java.util.ArrayList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = TMRVIngredientCollector.class, remap = false)
public interface TmrvCollectorAccessor {
    @Accessor("collectedIngredients")
    ArrayList<?> coo$collected();
}
