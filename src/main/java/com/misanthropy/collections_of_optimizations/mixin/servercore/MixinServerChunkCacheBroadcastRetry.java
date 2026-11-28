package com.misanthropy.collections_of_optimizations.mixin.servercore;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.BroadcastRetryQueue;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import me.wesley1808.servercore.common.interfaces.chunk.IServerChunkCache;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerChunkCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(value = ServerChunkCache.class, remap = false)
public abstract class MixinServerChunkCacheBroadcastRetry implements BroadcastRetryQueue {
    /** Chunks with block changes that have not been sent yet. Only chunks that changed are ever in here. */
    @Unique
    private final ReferenceOpenHashSet<ChunkHolder> coo$unsent = new ReferenceOpenHashSet<>();

    @Override
    public void coo$retryBroadcast(ChunkHolder holder) {
        this.coo$unsent.add(holder);
    }

    /** Before ServerCore sends this tick's changes, queue again every chunk whose changes are still waiting. */
    @Inject(method = "tickChunks()V", at = @At("HEAD"), require = 0)
    private void coo$requeueUnsent(CallbackInfo ci) {
        if (this.coo$unsent.isEmpty()) {
            return;
        }
        if (!CoOConfig.servercoreBroadcastRetry) {
            this.coo$unsent.clear();
            return;
        }
        IServerChunkCache cache = (IServerChunkCache) this;
        Iterator<ChunkHolder> it = this.coo$unsent.iterator();
        while (it.hasNext()) {
            ChunkHolder holder = it.next();
            if (((ChunkHolderChangedAccessor) holder).coo$hasChangedSections()
                    && holder.getFullChunkFuture().getNow(ChunkHolder.UNLOADED_LEVEL_CHUNK).isSuccess()) {
                cache.servercore$requiresBroadcast(holder);
            } else {
                it.remove();
            }
        }
    }
}
