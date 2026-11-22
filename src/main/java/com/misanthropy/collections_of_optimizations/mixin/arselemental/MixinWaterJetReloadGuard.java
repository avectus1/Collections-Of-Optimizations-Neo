package com.misanthropy.collections_of_optimizations.mixin.arselemental;

import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This spell entity keeps its spell only in memory; it is not written to the save. After a save and reload
 * (or when made with /summon) it comes back without its spell and crashes the server on its next tick.
 * A leftover like that has nothing left to cast, so remove it quietly instead.
 */
@Mixin(targets = "alexthw.ars_elemental.common.entity.spells.EntityWaterJet", remap = false)
public abstract class MixinWaterJetReloadGuard {
    @Shadow
    private SpellStats stats;
    @Shadow
    private com.hollingsworth.arsnouveau.api.spell.SpellContext spellContext;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void coo$dropSpellless(CallbackInfo ci) {
        if (CoOConfig.arselementalSpellEntityReloadGuard && (this.stats == null || this.spellContext == null)) {
            Entity self = (Entity) (Object) this;
            if (!self.level().isClientSide()) {
                self.discard();
                ci.cancel();
            }
        }
    }
}
