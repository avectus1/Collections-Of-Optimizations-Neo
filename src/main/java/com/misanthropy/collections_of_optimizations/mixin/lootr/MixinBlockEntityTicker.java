package com.misanthropy.collections_of_optimizations.mixin.lootr;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import noobanidus.mods.lootr.common.block.entity.BlockEntityTicker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.storage.loot.LootTable;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.adapter.ILootrDataAdapter;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;

import java.util.Map;
import java.util.Set;

/**
 * Only skips Lootr's conversion pass when it has nothing queued. Every waiting chest is still checked every
 * tick exactly like stock Lootr; capping or spacing out that pass left chests in new structures unconverted.
 */
@Mixin(value = BlockEntityTicker.class, remap = false)
public abstract class MixinBlockEntityTicker {
    @Shadow
    @Final
    private Map<?, ?> blockEntityEntries;

    @Shadow
    @Final
    private Map<?, ?> pendingEntries;

    @Inject(method = "onServerLevelTick", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$skipIdlePass(CallbackInfo ci) {
        if (!CoOConfig.lootrSkipIdleTileTicker || !this.blockEntityEntries.isEmpty()) {
            return;
        }
        synchronized (this.pendingEntries) {
            if (this.pendingEntries.isEmpty()) {
                ci.cancel();
            }
        }
    }

    /** Game time each chunk was first reported as not loaded while Lootr was waiting on it. */
    @Unique
    private final Long2LongOpenHashMap coo$unloadedSince = new Long2LongOpenHashMap();

    @Unique
    private static final long COO_GRACE_TICKS = 200L;

    /**
     * Lootr forgets a waiting chunk the first time the server says it is not loaded. Right after a chunk is
     * generated (and much more often with C2ME) that can be reported for a few ticks while the chunk is still
     * being promoted, and its loot chests then never convert. Treat it as still loading for a while instead.
     */
    @WrapOperation(
            method = "onServerLevelTick",
            at = @At(value = "INVOKE", target = "Lnoobanidus/mods/lootr/common/block/entity/BlockEntityTicker$Entry;getChunkLoadStatus(Lnet/minecraft/server/level/ServerLevel;Ljava/util/Set;)Lnoobanidus/mods/lootr/common/block/entity/BlockEntityTicker$ChunkLoadStatus;"),
            require = 0
    )
    private BlockEntityTicker.ChunkLoadStatus coo$graceUnloaded(BlockEntityTicker.Entry entry, ServerLevel level, Set<ChunkPos> loaded, Operation<BlockEntityTicker.ChunkLoadStatus> original) {
        BlockEntityTicker.ChunkLoadStatus status = original.call(entry, level, loaded);
        if (!CoOConfig.lootrKeepFreshChunks) {
            return status;
        }
        long key = entry.chunkPos().toLong();
        if (status != BlockEntityTicker.ChunkLoadStatus.UNLOADED) {
            if (!this.coo$unloadedSince.isEmpty()) {
                this.coo$unloadedSince.remove(key);
            }
            return status;
        }
        long now = level.getGameTime();
        long since = this.coo$unloadedSince.getOrDefault(key, Long.MIN_VALUE);
        if (since == Long.MIN_VALUE) {
            this.coo$unloadedSince.put(key, now);
            return BlockEntityTicker.ChunkLoadStatus.NOT_FULLY_LOADED;
        }
        if (now - since < COO_GRACE_TICKS) {
            return BlockEntityTicker.ChunkLoadStatus.NOT_FULLY_LOADED;
        }
        this.coo$unloadedSince.remove(key);
        return status;
    }

    /**
     * When Lootr turns one half of a double chest into a loot chest, queue the other half right away. Each half
     * is normally found through its own chunk, and when that chunk's entry was lost the second half stayed a
     * plain chest next to a Lootr chest until someone opened it. One block lookup per converted chest.
     */
    @Inject(method = "replaceEntity", at = @At("HEAD"), require = 0)
    private static void coo$queueOtherChestHalf(ServerLevel level, BlockPos pos, ILootrDataAdapter<BlockEntity> adapter, BlockEntity be, BlockState replacement, ResourceKey<LootTable> table, long seed, CallbackInfo ci) {
        if (!CoOConfig.lootrKeepFreshChunks) {
            return;
        }
        BlockState state = be.getBlockState();
        if (!(state.getBlock() instanceof ChestBlock) || !state.hasProperty(ChestBlock.TYPE) || state.getValue(ChestBlock.TYPE) == ChestType.SINGLE) {
            return;
        }
        BlockPos otherPos = pos.relative(ChestBlock.getConnectedDirection(state));
        if (!level.isLoaded(otherPos)) {
            return;
        }
        BlockEntity other = level.getBlockEntity(otherPos);
        if (other instanceof RandomizableContainer container && container.getLootTable() != null
                && !(LootrAPI.resolveBlockEntity(other) instanceof ILootrBlockEntity)) {
            BlockEntityTicker.addEntity(other, level, new ChunkPos(otherPos));
        }
    }
}
