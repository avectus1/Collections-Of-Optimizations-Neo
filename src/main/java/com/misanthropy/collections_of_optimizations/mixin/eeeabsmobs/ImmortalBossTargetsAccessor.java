package com.misanthropy.collections_of_optimizations.mixin.eeeabsmobs;

import com.eeeab.eeeabsmobs.server.entity.mob.immortal.EntityImmortalBoss;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = EntityImmortalBoss.class, remap = false)
public interface ImmortalBossTargetsAccessor {
    @Accessor("targets")
    List<LivingEntity> coo$targets();

    @Accessor("targets")
    void coo$setTargets(List<LivingEntity> targets);
}
