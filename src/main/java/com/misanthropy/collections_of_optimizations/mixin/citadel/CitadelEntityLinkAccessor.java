package com.misanthropy.collections_of_optimizations.mixin.citadel;

import com.github.alexthe666.citadel.client.gui.EntityLinkButton;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = EntityLinkButton.class, remap = false)
public interface CitadelEntityLinkAccessor {
    @Accessor("renderedEntities")
    static Map<?, ?> coo$renderedEntities() {
        throw new AssertionError();
    }
}
