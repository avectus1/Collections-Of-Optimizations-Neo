package com.misanthropy.collections_of_optimizations.mixin.toomanyrecipeviewers;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import dev.nolij.toomanyrecipeviewers.impl.jei.api.gui.builder.RecipeLayoutBuilder;
import dev.nolij.toomanyrecipeviewers.impl.recipe.TMRVRecipe;
import java.util.LinkedHashMap;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * After EMI bakes its recipe index, TMRV forgets each JEI recipe's inputs and outputs and rebuilds them on
 * demand, keeping every rebuilt copy behind a SoftReference. EMI reads them for every recipe once after the
 * bake, so all of them come back and stay: soft references are only cleared when the heap is nearly full,
 * which with a large heap means never, and in a big pack that is over 100 MB the GC keeps walking. Keep the
 * most recently used rebuilds in a small bounded cache instead (the recipes on screen), and rebuild the rest
 * when asked again.
 */
@Mixin(value = TMRVRecipe.class, remap = false)
public abstract class MixinTmrvRebuiltRecipeCache {
    @Unique
    private static final Map<Object, RecipeLayoutBuilder.ExtractedEMIRecipeData> COO$RECENT = new LinkedHashMap<>(64, 0.75F, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Object, RecipeLayoutBuilder.ExtractedEMIRecipeData> eldest) {
            return this.size() > CoOConfig.tmrvRebuiltRecipeCache;
        }
    };

    @Shadow
    private RecipeLayoutBuilder buildRecipe() {
        throw new AssertionError();
    }

    @Inject(method = "getRebuiltRecipeData", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$boundedRebuildCache(CallbackInfoReturnable<RecipeLayoutBuilder.ExtractedEMIRecipeData> cir) {
        if (CoOConfig.tmrvRebuiltRecipeCache <= 0) {
            return;
        }
        RecipeLayoutBuilder.ExtractedEMIRecipeData data;
        synchronized (COO$RECENT) {
            data = COO$RECENT.get(this);
        }
        if (data == null) {
            data = this.buildRecipe().extractEMIRecipeData();
            synchronized (COO$RECENT) {
                COO$RECENT.put(this, data);
            }
        }
        cir.setReturnValue(data);
    }
}
