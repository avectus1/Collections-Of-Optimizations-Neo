package com.misanthropy.collections_of_optimizations.mixin.mowziesmobs;

import com.misanthropy.collections_of_optimizations.core.MowzieIdleData;
import com.bobmowzie.mowziesmobs.client.ClientEventHandler;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/** Checking whether a rendered mob is frozen no longer creates Mowzie data on every mob on screen (see MowzieIdleData). */
@Mixin(value = ClientEventHandler.class, remap = false)
public abstract class MixinMowzieRenderFrozenCheck {
    @SuppressWarnings("rawtypes")
    @Redirect(method = "onRenderLiving", at = @At(value = "INVOKE", target = "Lcom/bobmowzie/mowziesmobs/server/capability/DataHandler;getData(Lnet/minecraft/world/entity/Entity;Lnet/neoforged/neoforge/registries/DeferredHolder;)Ljava/lang/Object;"))
    private static Object coo$idle(Entity entity, DeferredHolder type) {
        return MowzieIdleData.getForRender(entity, type);
    }
}
