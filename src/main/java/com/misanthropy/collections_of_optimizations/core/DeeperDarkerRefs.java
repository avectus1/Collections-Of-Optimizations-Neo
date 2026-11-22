package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.util.RandomSource;

public final class DeeperDarkerRefs {

    private static final ThreadLocal<RandomSource> SCRATCH_RANDOM = ThreadLocal.withInitial(RandomSource::create);

    private DeeperDarkerRefs() {
    }

    public static RandomSource scratchRandom() {
        return SCRATCH_RANDOM.get();
    }
}
