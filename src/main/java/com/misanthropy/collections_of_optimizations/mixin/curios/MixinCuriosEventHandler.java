package com.misanthropy.collections_of_optimizations.mixin.curios;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CuriosSlotCache;
import com.misanthropy.collections_of_optimizations.core.EntitySlotCacheHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.common.event.CuriosEventHandler;

@Mixin(value = CuriosEventHandler.class, remap = false)
public abstract class MixinCuriosEventHandler {
    @Inject(method = "tick(Lnet/neoforged/neoforge/event/tick/EntityTickEvent$Post;)V", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$skipRedundantCuriosTick(Post event, CallbackInfo ci) {
        if (!(event.getEntity() instanceof LivingEntity entity)) {
            return;
        }
        if (entity instanceof Player) {
            return;
        }
        if (CoOConfig.CURIOS_SKIP_CLIENT_TICK_ON_NON_PLAYERS.get() && entity.level().isClientSide()) {
            ci.cancel();
            return;
        }
        if (CoOConfig.CURIOS_SKIP_SLOTLESS_ENTITIES.get() && !coo$hasCurioSlots(entity)) {
            ci.cancel();
        }
    }

    @Unique
    private static boolean coo$hasCurioSlots(LivingEntity entity) {
        if (!CoOConfig.CURIOS_CACHE_ENTITY_SLOT_LOOKUP.get() || !(entity instanceof EntitySlotCacheHolder holder)) {
            return !CuriosApi.getEntitySlots(entity).isEmpty();
        }

        int generation = CuriosSlotCache.generation();
        if (holder.coo$slotCacheGeneration() == generation) {
            return holder.coo$slotCacheValue();
        }

        boolean hasSlots = !CuriosApi.getEntitySlots(entity).isEmpty();
        holder.coo$storeSlotCache(generation, hasSlots);
        return hasSlots;
    }
}
