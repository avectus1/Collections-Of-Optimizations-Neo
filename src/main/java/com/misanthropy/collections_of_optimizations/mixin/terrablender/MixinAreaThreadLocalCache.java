package com.misanthropy.collections_of_optimizations.mixin.terrablender;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import it.unimi.dsi.fastutil.HashCommon;
import net.minecraft.world.level.ChunkPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terrablender.worldgen.noise.Area;
import terrablender.worldgen.noise.PixelTransformer;

import java.util.Arrays;

@Mixin(value = Area.class, remap = false)
public abstract class MixinAreaThreadLocalCache {

    @Shadow
    @Final
    private int mask;

    @Shadow
    @Final
    private PixelTransformer operator;

    @Unique
    private ThreadLocal<long[]> coo$slots;

    @Inject(method = "<init>(Lterrablender/worldgen/noise/PixelTransformer;I)V", at = @At("TAIL"), require = 0)
    private void coo$initSlots(PixelTransformer operator, int size, CallbackInfo ci) {
        int length = (this.mask + 1) * 2;
        this.coo$slots = ThreadLocal.withInitial(() -> {
            long[] slots = new long[length];
            Arrays.fill(slots, Long.MIN_VALUE);
            return slots;
        });
    }

    @Inject(method = "get(II)I", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$threadLocalGet(int x, int z, CallbackInfoReturnable<Integer> cir) {
        if (!CoOConfig.terrablenderThreadLocalAreaCache || this.coo$slots == null) {
            return;
        }
        long key = ChunkPos.asLong(x, z);
        int idx = ((int) HashCommon.mix(key) & this.mask) * 2;
        long[] slots = this.coo$slots.get();
        if (slots[idx] == key) {
            cir.setReturnValue((int) slots[idx + 1]);
            return;
        }
        int value = this.operator.apply(x, z);
        slots[idx] = key;
        slots[idx + 1] = value;
        cir.setReturnValue(value);
    }
}
