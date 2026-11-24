package com.misanthropy.collections_of_optimizations.mixin.enderscape;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.bunten.enderscape.block.sound.SoundTypeOverride;
import net.bunten.enderscape.registry.EnderscapeSoundTypeOverrides;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/**
 * Enderscape checks every block's sound type (every footstep, place, break, fall) against a plain list of
 * "no override" block states that grows to thousands of entries, scanning it from the start each time, and
 * shares unsynchronised maps between the server and render threads. One concurrent lookup per state instead,
 * caching the exact same answers.
 */
@Mixin(value = EnderscapeSoundTypeOverrides.class, remap = false)
public abstract class MixinEnderscapeSoundTypeCache {
    @Shadow
    @Final
    public static List<SoundTypeOverride> SOUND_TYPE_OVERRIDES;

    @Unique
    private static final ConcurrentHashMap<BlockState, Optional<SoundType>> coo$RESULTS = new ConcurrentHashMap<>();

    /**
     * @author collections_of_optimizations
     * @reason constant-time cached lookup, same results
     */
    @Overwrite
    public static Optional<SoundType> getSoundType(BlockState state) {
        Optional<SoundType> cached = coo$RESULTS.get(state);
        if (cached != null) {
            return cached;
        }
        Optional<SoundType> result = Optional.empty();
        for (SoundTypeOverride override : SOUND_TYPE_OVERRIDES) {
            if (override.applies(state)) {
                result = Optional.ofNullable(override.getSoundType());
                break;
            }
        }
        if (CoOConfig.enderscapeSoundTypeCache) {
            coo$RESULTS.put(state, result);
        }
        return result;
    }
}
