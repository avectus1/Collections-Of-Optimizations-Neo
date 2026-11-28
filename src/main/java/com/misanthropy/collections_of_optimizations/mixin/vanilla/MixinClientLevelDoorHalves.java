package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * The client gets each half of a door from the server as its own block update, and it does not run shape
 * updates for those. When one of the two updates is lost around the client's own prediction of a click, one
 * half stays open and the other closed until the chunk is sent again (relog). Both halves of a door always
 * share facing, hinge, open and powered, so bring the half the server did not tell us about in line with the
 * half it did.
 * <p>
 * While a click is being predicted, the client keeps for each changed block the last state the server sent.
 * For a half the server sent nothing about during the click, that is only the state from before the click,
 * so it must never be copied onto the other half: only a half that really got a server update this click is
 * trusted. Copying the stale half used to snap a door that the server had opened back to closed.
 */
@Mixin(value = ClientLevel.class, remap = false)
public abstract class MixinClientLevelDoorHalves {
    @Shadow
    @Final
    private BlockStatePredictionHandler blockStatePredictionHandler;

    /** Door halves the server sent an update for while a click on them was still waiting for its ack. */
    @Unique
    private final LongOpenHashSet coo$freshWhilePredicting = new LongOpenHashSet();

    /** Fresh door halves already applied by an ack whose other half was still waiting in the same ack. */
    @Unique
    private final LongOpenHashSet coo$freshAcked = new LongOpenHashSet();

    @Inject(method = "setServerVerifiedBlockState", at = @At("HEAD"), require = 0)
    private void coo$noteFreshDoorHalf(BlockPos pos, BlockState state, int flags, CallbackInfo ci) {
        if (CoOConfig.vanillaClientDoorHalfSync && state.getBlock() instanceof DoorBlock
                && this.coo$pending().containsKey(pos.asLong())) {
            this.coo$freshWhilePredicting.add(pos.asLong());
        }
    }

    @Inject(method = "setServerVerifiedBlockState", at = @At("TAIL"), require = 0)
    private void coo$syncDoorFromServer(BlockPos pos, BlockState state, int flags, CallbackInfo ci) {
        if (!CoOConfig.vanillaClientDoorHalfSync || !(state.getBlock() instanceof DoorBlock)) {
            return;
        }
        ClientLevel level = (ClientLevel) (Object) this;
        // A predicted block shows the client's guess until the ack arrives; the ack handles it below.
        if (this.blockStatePredictionHandler.isPredicting() || level.getBlockState(pos) != state) {
            return;
        }
        BlockPos otherPos = coo$otherHalf(pos, state);
        Long2ObjectOpenHashMap<?> pending = this.coo$pending();
        if (pending.containsKey(pos.asLong()) || pending.containsKey(otherPos.asLong())) {
            return;
        }
        coo$copyOnto(level, state, otherPos);
    }

    @Inject(method = "syncBlockState", at = @At("TAIL"), require = 0)
    private void coo$syncDoorFromAck(BlockPos pos, BlockState state, Vec3 playerPos, CallbackInfo ci) {
        long key = pos.asLong();
        boolean fresh = this.coo$freshWhilePredicting.remove(key);
        if (!CoOConfig.vanillaClientDoorHalfSync || !(state.getBlock() instanceof DoorBlock)) {
            this.coo$freshAcked.remove(key);
            return;
        }
        ClientLevel level = (ClientLevel) (Object) this;
        BlockPos otherPos = coo$otherHalf(pos, state);
        long otherKey = otherPos.asLong();
        boolean otherWaiting = this.coo$pending().containsKey(otherKey);
        if (fresh) {
            if (otherWaiting) {
                // The other half is applied later in this same ack; let it decide once it knows its own data.
                this.coo$freshAcked.add(key);
            } else if (level.getBlockState(pos) == state) {
                coo$copyOnto(level, state, otherPos);
            }
        } else if (!otherWaiting && this.coo$freshAcked.remove(otherKey)) {
            // This half only held its state from before the click; the other half is what the server said.
            BlockState other = level.getBlockState(otherPos);
            if (other.getBlock() instanceof DoorBlock) {
                coo$copyOnto(level, other, pos);
            }
        }
        this.coo$freshAcked.remove(key);
    }

    @Unique
    private Long2ObjectOpenHashMap<?> coo$pending() {
        return ((PredictionHandlerAccessor) this.blockStatePredictionHandler).coo$serverVerifiedStates();
    }

    @Unique
    private static BlockPos coo$otherHalf(BlockPos pos, BlockState state) {
        return state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER ? pos.above() : pos.below();
    }

    /** Sets the door half at {@code target} to match {@code source}, if it is the matching other half. */
    @Unique
    private static void coo$copyOnto(ClientLevel level, BlockState source, BlockPos target) {
        BlockState other = level.getBlockState(target);
        if (other.getBlock() != source.getBlock() || other.getValue(DoorBlock.HALF) == source.getValue(DoorBlock.HALF)) {
            return;
        }
        BlockState wanted = source.setValue(DoorBlock.HALF, other.getValue(DoorBlock.HALF));
        if (other != wanted) {
            level.setBlock(target, wanted, Block.UPDATE_ALL_IMMEDIATE | Block.UPDATE_KNOWN_SHAPE);
        }
    }
}
