package com.misanthropy.collections_of_optimizations.mixin.cataclysm;

import com.github.L_Ender.cataclysm.entity.etc.Animation_Monsters;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Animation_Monsters.class, remap = false)
public abstract class MixinCataclysmBossMusic {

    @Unique
    private boolean coo$stopSent;

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/neoforged/neoforge/network/PacketDistributor;sendToPlayersTrackingEntityAndSelf(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;[Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;)V",
                    ordinal = 0
            ),
            require = 0
    )
    private void coo$playMusic(Entity entity, CustomPacketPayload message, CustomPacketPayload[] others, Operation<Void> original) {
        this.coo$stopSent = false;
        original.call(entity, message, others);
    }

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/neoforged/neoforge/network/PacketDistributor;sendToPlayersTrackingEntityAndSelf(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;[Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;)V",
                    ordinal = 1
            ),
            require = 0
    )
    private void coo$stopMusicOnce(Entity entity, CustomPacketPayload message, CustomPacketPayload[] others, Operation<Void> original) {
        if (CoOConfig.cataclysmBossMusicOnChange && this.coo$stopSent) {
            return;
        }
        this.coo$stopSent = true;
        original.call(entity, message, others);
    }
}
