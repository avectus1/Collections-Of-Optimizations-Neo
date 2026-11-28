package com.misanthropy.collections_of_optimizations.mixin.servercore;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.BroadcastRetryQueue;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelHeightAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * ServerCore only queues a chunk for sending its block changes on the first change of a section. If the chunk is
 * not ticking when ServerCore sends (common with C2ME's no-tick view distance), the queue is cleared but the
 * changes stay waiting, and later changes in the same section never queue it again. Every block change in that
 * section is then lost for players until they relog: doors stuck half open or snapping back, Lootr chests that
 * still look like normal chests. Remember such chunks and queue them again until their changes are sent.
 */
@Mixin(value = ChunkHolder.class, remap = false)
public abstract class MixinChunkHolderBroadcastRetry {
    @Shadow
    @Final
    private LevelHeightAccessor levelHeightAccessor;

    @Shadow
    private boolean hasChangedSections;

    @Inject(method = "blockChanged", at = @At("TAIL"), require = 0)
    private void coo$rememberUnsent(BlockPos pos, CallbackInfo ci) {
        if (CoOConfig.servercoreBroadcastRetry && this.hasChangedSections
                && this.levelHeightAccessor instanceof ServerLevel level) {
            ((BroadcastRetryQueue) level.getChunkSource()).coo$retryBroadcast((ChunkHolder) (Object) this, level.getServer().isSameThread());
        }
    }
}
