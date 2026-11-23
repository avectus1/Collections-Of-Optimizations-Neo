package com.misanthropy.collections_of_optimizations.mixin.dungeonnowloading;

import dev.hexnowloading.dungeonnowloading.potion.VertexTransmissionEffect;
import java.util.HashMap;
import java.util.UUID;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = VertexTransmissionEffect.class, remap = false)
public interface DnlVertexEffectAccessor {
    @Invoker("cleanup")
    void coo$cleanup(LivingEntity entity);

    @Accessor("entityVertexNodeMap")
    static HashMap<UUID, ?> coo$nodes() { throw new AssertionError(); }

    @Accessor("damageTickCountMap")
    static HashMap<UUID, ?> coo$damageTicks() { throw new AssertionError(); }

    @Accessor("isReconnectionCaseMap")
    static HashMap<UUID, ?> coo$reconnection() { throw new AssertionError(); }

    @Accessor("isNoConnectionBeamDamageCaseMap")
    static HashMap<UUID, ?> coo$noConnection() { throw new AssertionError(); }
}
