package com.misanthropy.collections_of_optimizations.mixin.notenoughanimations;

import com.misanthropy.collections_of_optimizations.core.NeaFreezeMemo;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = AbstractClientPlayer.class, remap = false)
public abstract class MixinPlayerFreezeMemoSlot implements NeaFreezeMemo {
    @Unique
    private int coo$freezeTick = Integer.MIN_VALUE;
    @Unique
    private byte coo$freezeValue;

    @Override
    public int coo$freezeTick() {
        return this.coo$freezeTick;
    }

    @Override
    public byte coo$freezeValue() {
        return this.coo$freezeValue;
    }

    @Override
    public void coo$setFreeze(int tick, byte value) {
        this.coo$freezeValue = value;
        this.coo$freezeTick = tick;
    }
}
