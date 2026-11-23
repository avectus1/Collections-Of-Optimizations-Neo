package com.misanthropy.collections_of_optimizations.mixin.alexsmobs;

import com.github.alexthe666.alexsmobs.world.AMWorldData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = AMWorldData.class, remap = false)
public abstract class MixinAMWorldDataOverworldKey {
    /**
     * The cache is read with the overworld as key but written with whichever dimension asked, so outside the
     * overworld it never hits: every call looks the data up again and marks it dirty, and each dimension
     * adds its own entry. Store it under the overworld, the key it is read with.
     */
    @ModifyArg(method = "get", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"), index = 0)
    private static Object coo$overworldKey(Object key) {
        if (com.misanthropy.collections_of_optimizations.CoOConfig.alexsmobsLeakGuard && key instanceof ServerLevel level) {
            ServerLevel overworld = level.getServer().getLevel(Level.OVERWORLD);
            if (overworld != null) {
                return overworld;
            }
        }
        return key;
    }
}
