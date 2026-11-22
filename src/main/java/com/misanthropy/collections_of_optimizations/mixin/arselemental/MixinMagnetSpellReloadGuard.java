package com.misanthropy.collections_of_optimizations.mixin.arselemental;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** The lingering magnet keeps its target filter only in memory; after a reload (or /summon) it is gone and the magnet crashes the server. Remove such leftovers. */
@Mixin(targets = "alexthw.ars_elemental.common.entity.spells.EntityMagnetSpell", remap = false)
public abstract class MixinMagnetSpellReloadGuard {
    @Shadow
    List<Predicate<Entity>> ignored;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void coo$dropSpellless(CallbackInfo ci) {
        if (CoOConfig.arselementalSpellEntityReloadGuard && this.ignored == null) {
            Entity self = (Entity) (Object) this;
            if (!self.level().isClientSide()) {
                self.discard();
                ci.cancel();
            }
        }
    }
}
