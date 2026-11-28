package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.server.level.ChunkHolder;

/** Implemented on ServerChunkCache when ServerCore is installed. */
public interface BroadcastRetryQueue {
    void coo$retryBroadcast(ChunkHolder holder, boolean serverThread);
}
