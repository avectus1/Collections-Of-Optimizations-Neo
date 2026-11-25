package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * The client gets each half of a door from the server as its own block update, and it does not run shape
 * updates for those. When one of the two updates is lost or applied out of order around the client's own
 * prediction of a click, one half stays open and the other closed until the chunk is sent again (relog).
 * Both halves of a door always share facing, hinge, open and powered, so whenever the server tells the
 * client what one half is, bring the other half in line with it.
 */
@Mixin(value = ClientLevel.class, remap = false)
public abstract class MixinClientLevelDoorHalves {

    @Inject(method = "setServerVerifiedBlockState", at = @At("TAIL"), require = 0)
    private void coo$syncDoorFromServer(BlockPos pos, BlockState state, int flags, CallbackInfo ci) {
        this.coo$matchOtherHalf(pos, state);
    }

    @Inject(method = "syncBlockState", at = @At("TAIL"), require = 0)
    private void coo$syncDoorFromAck(BlockPos pos, BlockState state, Vec3 playerPos, CallbackInfo ci) {
        this.coo$matchOtherHalf(pos, state);
    }

    @Unique
    private void coo$matchOtherHalf(BlockPos pos, BlockState state) {
        if (!CoOConfig.vanillaClientDoorHalfSync || !(state.getBlock() instanceof DoorBlock)) {
            return;
        }
        ClientLevel level = (ClientLevel) (Object) this;
        boolean lower = state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER;
        BlockPos otherPos = lower ? pos.above() : pos.below();
        BlockState other = level.getBlockState(otherPos);
        if (other.getBlock() != state.getBlock() || other.getValue(DoorBlock.HALF) == state.getValue(DoorBlock.HALF)) {
            return;
        }
        BlockState wanted = state.setValue(DoorBlock.HALF, lower ? DoubleBlockHalf.UPPER : DoubleBlockHalf.LOWER);
        if (other != wanted) {
            level.setBlock(otherPos, wanted, Block.UPDATE_ALL_IMMEDIATE | Block.UPDATE_KNOWN_SHAPE);
        }
    }
}
