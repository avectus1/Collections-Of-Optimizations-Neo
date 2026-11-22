package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.network.protocol.game.VecDeltaCodec;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = VecDeltaCodec.class, remap = false)
public abstract class MixinVecDeltaCodec {

    // Entities that have not moved return the shared zero vector instead of a fresh subtraction result.
    @Redirect(
            method = "delta",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;subtract(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;"),
            require = 0
    )
    private Vec3 coo$skipZeroDelta(Vec3 position, Vec3 base) {
        if (CoOConfig.vanillaLeanTrackerDelta && position.x == base.x && position.y == base.y && position.z == base.z) {
            return Vec3.ZERO;
        }
        return position.subtract(base);
    }
}
