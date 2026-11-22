package com.misanthropy.collections_of_optimizations.mixin.majruszsdifficulty;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.MobCapLimitMemo;
import net.minecraft.world.entity.MobCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.world.level.LocalMobCapCalculator$MobCounts", remap = false)
public abstract class MixinMobCountsLimitMemo {
    @Redirect(method = "canSpawn",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/MobCategory;getMaxInstancesPerChunk()I"))
    private int coo$limit(MobCategory category) {
        return CoOConfig.majruszsdifficultySpawnLimitMemo ? MobCapLimitMemo.get(category) : category.getMaxInstancesPerChunk();
    }
}
