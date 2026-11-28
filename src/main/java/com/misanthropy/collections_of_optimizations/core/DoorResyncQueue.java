package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.server.level.ServerLevel;

/** Implemented on Level: door halves waiting to be sent to players again. */
public interface DoorResyncQueue {
    void coo$flushDoorResync(ServerLevel level);
}
