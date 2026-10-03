package com.misanthropy.collections_of_optimizations.mixin.artifacts;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import artifacts.neoforge.event.ArtifactHooksNeoForge;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ArtifactHooksNeoForge.class, remap = false)
public abstract class MixinArtifactHooksNeoForge {

    @Inject(
            method = "onLivingUpdate(Lnet/neoforged/neoforge/event/tick/EntityTickEvent$Post;)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipClientTickOnNonPlayers(EntityTickEvent.Post event, CallbackInfo ci) {
        Entity entity = event.getEntity();
        if (CoOConfig.artifactsSkipClientTickOnNonPlayers
                && entity instanceof LivingEntity
                && entity.level().isClientSide()
                && !(entity instanceof Player)) {
            ci.cancel();
        }
    }
}
