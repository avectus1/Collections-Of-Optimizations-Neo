package com.misanthropy.collections_of_optimizations.mixin.ironsspellbooks;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.jei.ArcaneAnvilJeiRecipe;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * The recipe viewer's Arcane Anvil "imbue" pages show every spell at every level for every weapon that can be
 * imbued, and each of those item copies gets its own freshly built spell data. In a big pack that is hundreds of
 * thousands of identical, unchangeable spell containers kept in memory. Build one per spell and level and share it.
 * The items and recipes shown are exactly the same.
 */
@Mixin(value = ArcaneAnvilJeiRecipe.class, remap = false)
public abstract class MixinIsbAnvilRecipeSharedScrolls {
    @Unique
    private static final ConcurrentHashMap<AbstractSpell, ISpellContainer[]> coo$SHARED = new ConcurrentHashMap<>();

    @Redirect(method = "*", at = @At(value = "INVOKE", target = "Lio/redspace/ironsspellbooks/api/spells/ISpellContainer;createScrollContainer(Lio/redspace/ironsspellbooks/api/spells/AbstractSpell;ILnet/minecraft/world/item/ItemStack;)Lio/redspace/ironsspellbooks/api/spells/ISpellContainer;"))
    private static ISpellContainer coo$shared(AbstractSpell spell, int level, ItemStack stack) {
        if (!CoOConfig.isbSharedRecipeScrolls || spell == null || level < 0 || level > 64) {
            return ISpellContainer.createScrollContainer(spell, level, stack);
        }
        ISpellContainer[] byLevel = coo$SHARED.computeIfAbsent(spell, s -> new ISpellContainer[65]);
        ISpellContainer container = byLevel[level];
        if (container == null) {
            container = ISpellContainer.createScrollContainer(spell, level, stack);
            byLevel[level] = container;
            return container;
        }
        ISpellContainer.set(stack, container);
        return container;
    }
}
