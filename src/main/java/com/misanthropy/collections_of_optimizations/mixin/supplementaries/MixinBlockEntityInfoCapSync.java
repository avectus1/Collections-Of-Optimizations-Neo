package com.misanthropy.collections_of_optimizations.mixin.supplementaries;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData$BlockEntityInfo", priority = 1500, remap = false)
public abstract class MixinBlockEntityInfoCapSync {
    @TargetHandler(mixin = "net.mehvahdjukaar.supplementaries.mixins.neoforge.ChunkHolderMixin", name = "sendBlockEntityCaps")
    @WrapMethod(method = "@MixinSquared:Handler", require = 0)
    private static void coo$skipNonSignCapSync(BlockEntity te, CallbackInfoReturnable<?> cir, Operation<Void> original) {
        if (CoOConfig.supplementariesSkipNonSignCapSync && !(te instanceof SignBlockEntity)) {
            return;
        }
        original.call(te, cir);
    }
}
