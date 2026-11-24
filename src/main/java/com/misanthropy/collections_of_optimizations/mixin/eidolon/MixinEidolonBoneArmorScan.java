package com.misanthropy.collections_of_optimizations.mixin.eidolon;

import alexthw.eidolon_repraised.event.Events;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Eidolon scans every living entity's armor for Bonelord armor every tick, but only uses the answer on
 * ticks divisible by 120. Skip the scan on the other 119.
 */
@Mixin(value = Events.class, remap = false)
public abstract class MixinEidolonBoneArmorScan {
    @Redirect(method = "onTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getArmorSlots()Ljava/lang/Iterable;"))
    private Iterable<ItemStack> coo$armorOnlyWhenUsed(LivingEntity entity) {
        if (CoOConfig.eidolonBoneArmorScan && entity.tickCount % 120 != 0) {
            return List.of();
        }
        return entity.getArmorSlots();
    }
}
