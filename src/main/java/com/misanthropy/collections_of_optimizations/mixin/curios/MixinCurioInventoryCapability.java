package com.misanthropy.collections_of_optimizations.mixin.curios;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CurioPresenceCache;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.common.capability.CurioInventoryCapability;

import java.util.Optional;

@Mixin(value = CurioInventoryCapability.class, remap = false)
public abstract class MixinCurioInventoryCapability {
    @Inject(method = "findFirstCurio(Lnet/minecraft/world/item/Item;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$fastFindFirstMiss(Item item, CallbackInfoReturnable<Optional<SlotResult>> cir) {
        if (!CoOConfig.curiosFastFindFirstMiss) {
            return;
        }
        LivingEntity wearer = ((CurioInventoryCapability) (Object) this).getWearer();
        if (wearer != null && !CurioPresenceCache.mayHaveEquipped(wearer, item)) {
            cir.setReturnValue(Optional.empty());
        }
    }
}
