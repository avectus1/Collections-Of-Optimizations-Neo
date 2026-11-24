package com.misanthropy.collections_of_optimizations.mixin.toomanyrecipeviewers;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import dev.nolij.toomanyrecipeviewers.impl.jei.api.gui.builder.TMRVIngredientCollector;
import dev.nolij.toomanyrecipeviewers.impl.jei.api.gui.ingredient.TMRVSlotWidget;
import dev.emi.emi.api.stack.EmiIngredient;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Every time EMI draws or checks a recipe slot that came from a JEI plugin, TMRV rebuilds the slot's whole item list
 * (a new EMI stack for every item that can go there, which for tag slots can be hundreds), several times per slot per
 * frame. The list only changes when the slot's contents change, so build it once and rebuild only when they do.
 */
@Mixin(value = TMRVSlotWidget.class, remap = false)
public abstract class MixinTmrvSlotStackMemo {
    @Unique
    private TMRVIngredientCollector coo$collector;
    @Unique
    private int coo$size = -1;
    @Unique
    private Map<?, ?> coo$overrides;
    @Unique
    private int coo$overridesSize = -1;
    @Unique
    private EmiIngredient coo$stack;

    @Redirect(method = "getStack", at = @At(value = "INVOKE", target = "Ldev/nolij/toomanyrecipeviewers/impl/jei/api/gui/builder/TMRVIngredientCollector;getEMIIngredient(Ljava/util/Map;)Ldev/emi/emi/api/stack/EmiIngredient;"))
    @SuppressWarnings({"rawtypes", "unchecked"})
    private EmiIngredient coo$memo(TMRVIngredientCollector collector, Map overrides) {
        if (!CoOConfig.tmrvSlotStackMemo) {
            return collector.getEMIIngredient(overrides);
        }
        int size = ((TmrvCollectorAccessor) (Object) collector).coo$collected().size();
        int overridesSize = overrides == null ? -1 : overrides.size();
        EmiIngredient stack = this.coo$stack;
        if (stack == null || collector != this.coo$collector || size != this.coo$size || overrides != this.coo$overrides || overridesSize != this.coo$overridesSize) {
            stack = collector.getEMIIngredient(overrides);
            this.coo$stack = stack;
            this.coo$collector = collector;
            this.coo$size = size;
            this.coo$overrides = overrides;
            this.coo$overridesSize = overridesSize;
        }
        return stack;
    }
}
