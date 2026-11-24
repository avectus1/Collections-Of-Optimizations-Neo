package com.misanthropy.collections_of_optimizations.mixin.malum;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.sammy.malum.common.item.curiosities.curios.sets.weeping.CurioHiddenBladeNecklace;
import com.sammy.malum.common.item.curiosities.curios.sets.weeping.CurioWatcherNecklace;
import com.sammy.malum.core.handlers.GeasEffectHandler;
import com.sammy.malum.core.handlers.SoulDataHandler;
import com.sammy.malum.core.handlers.TouchOfDarknessHandler;
import com.sammy.malum.events.RuntimeEvents;
import com.sammy.malum.registry.common.MalumAttachmentTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Malum's living tick calls getData on every mob, every tick, for soul info, curio cooldowns, touch of
 * darkness and geas. That creates (and later saves) four or five data objects on every mob in the world
 * just to count down timers that start at zero. When an entity has never been given that data, its tick
 * would do nothing, so skip it. Geas is only skipped for non-players, since a player's first geas tick
 * also sets up its curio slot.
 */
@Mixin(value = RuntimeEvents.class, remap = false)
public abstract class MixinMalumIdleLivingTick {
    @Redirect(method = "onLivingTick", at = @At(value = "INVOKE", target = "Lcom/sammy/malum/core/handlers/SoulDataHandler;entityTick(Lnet/neoforged/neoforge/event/tick/EntityTickEvent$Pre;)V"))
    private static void coo$soul(EntityTickEvent.Pre event) {
        if (!CoOConfig.malumIdleLivingTick || event.getEntity().hasData(MalumAttachmentTypes.LIVING_SOUL_INFO)) {
            SoulDataHandler.entityTick(event);
        }
    }

    @Redirect(method = "onLivingTick", at = @At(value = "INVOKE", target = "Lcom/sammy/malum/common/item/curiosities/curios/sets/weeping/CurioWatcherNecklace;entityTick(Lnet/neoforged/neoforge/event/tick/EntityTickEvent$Pre;)V"))
    private static void coo$watcher(EntityTickEvent.Pre event) {
        if (!CoOConfig.malumIdleLivingTick || event.getEntity().hasData(MalumAttachmentTypes.CURIO_DATA)) {
            CurioWatcherNecklace.entityTick(event);
        }
    }

    @Redirect(method = "onLivingTick", at = @At(value = "INVOKE", target = "Lcom/sammy/malum/common/item/curiosities/curios/sets/weeping/CurioHiddenBladeNecklace;entityTick(Lnet/neoforged/neoforge/event/tick/EntityTickEvent$Pre;)V"))
    private static void coo$hiddenBlade(EntityTickEvent.Pre event) {
        if (!CoOConfig.malumIdleLivingTick || event.getEntity().hasData(MalumAttachmentTypes.CURIO_DATA)) {
            CurioHiddenBladeNecklace.entityTick(event);
        }
    }

    @Redirect(method = "onLivingTick", at = @At(value = "INVOKE", target = "Lcom/sammy/malum/core/handlers/TouchOfDarknessHandler;entityTick(Lnet/neoforged/neoforge/event/tick/EntityTickEvent$Pre;)V"))
    private static void coo$touch(EntityTickEvent.Pre event) {
        if (!CoOConfig.malumIdleLivingTick || event.getEntity().hasData(MalumAttachmentTypes.TOUCH_OF_DARKNESS)) {
            TouchOfDarknessHandler.entityTick(event);
        }
    }

    @Redirect(method = "onLivingTick", at = @At(value = "INVOKE", target = "Lcom/sammy/malum/core/handlers/GeasEffectHandler;entityTick(Lnet/neoforged/neoforge/event/tick/EntityTickEvent$Pre;)V"))
    private static void coo$geas(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();
        if (!CoOConfig.malumIdleLivingTick || entity instanceof Player || entity.hasData(MalumAttachmentTypes.GEAS_SOUL_INFO)) {
            GeasEffectHandler.entityTick(event);
        }
    }
}
