package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.DoorResyncQueue;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Remembers server-side door changes so both halves can be sent to players again a few ticks later, after the
 * click that caused them has been acknowledged. Whatever made a client miss one half, the resend puts it right.
 */
@Mixin(value = Level.class, remap = false)
public abstract class MixinLevelDoorResync implements DoorResyncQueue {
    @Unique
    private static final int COO_DELAY = 3;

    @Unique
    private Long2LongOpenHashMap coo$doorResync;

    @Inject(method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z", at = @At("HEAD"), require = 0)
    private void coo$noteDoorChange(BlockPos pos, BlockState state, int flags, int recursion, CallbackInfoReturnable<Boolean> cir) {
        if (!CoOConfig.vanillaServerDoorResync || !(state.getBlock() instanceof DoorBlock)) {
            return;
        }
        Level level = (Level) (Object) this;
        // Only doors changed by the game itself; structure placement on worldgen threads needs no resend, and the
        // queue is not thread safe.
        if (!(level instanceof ServerLevel serverLevel) || !serverLevel.getServer().isSameThread()) {
            return;
        }
        if (this.coo$doorResync == null) {
            this.coo$doorResync = new Long2LongOpenHashMap();
        }
        long due = level.getGameTime() + COO_DELAY;
        BlockPos other = state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER ? pos.above() : pos.below();
        this.coo$doorResync.put(pos.asLong(), due);
        this.coo$doorResync.put(other.asLong(), due);
    }

    @Override
    public void coo$flushDoorResync(ServerLevel level) {
        Long2LongOpenHashMap queue = this.coo$doorResync;
        if (queue == null || queue.isEmpty()) {
            return;
        }
        long now = level.getGameTime();
        ObjectIterator<Long2LongMap.Entry> it = queue.long2LongEntrySet().fastIterator();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        while (it.hasNext()) {
            Long2LongMap.Entry e = it.next();
            if (e.getLongValue() > now) {
                continue;
            }
            // Read the key before removing: the iterator's entry is a view that removal shifts.
            long key = e.getLongKey();
            it.remove();
            pos.set(key);
            if (level.isLoaded(pos)) {
                level.getChunkSource().blockChanged(pos);
            }
        }
    }
}
