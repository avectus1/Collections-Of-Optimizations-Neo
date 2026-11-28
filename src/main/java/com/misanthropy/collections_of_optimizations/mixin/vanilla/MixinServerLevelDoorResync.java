package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.core.DoorResyncQueue;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(value = ServerLevel.class, remap = false)
public abstract class MixinServerLevelDoorResync {
    @Inject(method = "tick", at = @At("HEAD"), require = 0)
    private void coo$resendDoors(BooleanSupplier hasTimeLeft, CallbackInfo ci) {
        ((DoorResyncQueue) this).coo$flushDoorResync((ServerLevel) (Object) this);
    }
}
