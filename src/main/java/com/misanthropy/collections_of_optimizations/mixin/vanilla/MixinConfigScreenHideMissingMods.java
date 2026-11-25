package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.misanthropy.collections_of_optimizations.ClientConfigScreen;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * NeoForge's config screen does not pass sub-sections through the screen filter, so CoO's own screen hides
 * the top-level sections of mods that are not installed here instead. Only touches CoO's config.
 */
@Mixin(value = ConfigurationScreen.ConfigurationSectionScreen.class, remap = false)
public abstract class MixinConfigScreenHideMissingMods {
    @Shadow
    @Final
    protected ConfigurationScreen.ConfigurationSectionScreen.Context context;

    @Inject(method = "createSection", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$hideMissingMods(String key, UnmodifiableConfig subconfig, UnmodifiableConfig subsection,
                                     CallbackInfoReturnable<ConfigurationScreen.ConfigurationSectionScreen.Element> cir) {
        if ("collections_of_optimizations".equals(this.context.modId())
                && this.context.keylist().isEmpty()
                && ClientConfigScreen.isTopLevelSection(key)
                && !ClientConfigScreen.sectionApplies(key)) {
            cir.setReturnValue(null);
        }
    }
}
