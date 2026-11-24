package com.misanthropy.collections_of_optimizations.mixin.mowziesmobs;

import com.misanthropy.collections_of_optimizations.core.MowzieIdleData;
import com.bobmowzie.mowziesmobs.server.ServerEventHandler;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/** See MowzieIdleData. */
@Mixin(value = ServerEventHandler.class, remap = false)
public abstract class MixinMowzieIdleEntityData {
    @SuppressWarnings("rawtypes")
    @Redirect(method = "onLivingTick", at = @At(value = "INVOKE", target = "Lcom/bobmowzie/mowziesmobs/server/capability/DataHandler;getData(Lnet/minecraft/world/entity/Entity;Lnet/neoforged/neoforge/registries/DeferredHolder;)Ljava/lang/Object;"))
    private Object coo$idle(Entity entity, DeferredHolder type) {
        return MowzieIdleData.getForTick(entity, type);
    }
}
