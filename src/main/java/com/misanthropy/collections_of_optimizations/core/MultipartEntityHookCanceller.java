package com.misanthropy.collections_of_optimizations.core;

import com.bawnorton.mixinsquared.api.MixinCanceller;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Swaps CERBON's API multipart hook on Entity (a cancellable RETURN inject on getBoundingBox plus an
 * interface instanceof on every call) for MixinEntityMultipartHooks, which does the same work behind a
 * per-entity cached flag. If this canceller never runs, the replacement stays inert (see {@link #ACTIVE}).
 */
public final class MultipartEntityHookCanceller implements MixinCanceller {
    private static final Logger LOGGER = LoggerFactory.getLogger("collections_of_optimizations");
    private static final String TARGET = "com.cerbon.cerbons_api.mixin.multipart_entities.EntityMixin";
    private static final String REPLACEMENT = "com.misanthropy.collections_of_optimizations.mixin.cerbonsapi.MixinEntityMultipartHooks";
    public static volatile boolean ACTIVE;

    @Override
    public boolean shouldCancel(List<String> targetClassNames, String mixinClassName) {
        if (!TARGET.equals(mixinClassName) || "false".equals(System.getProperty("coo.cerbonsMultipartHook"))
                || !CoOMixinPlugin.wouldApply(REPLACEMENT)) {
            return false;
        }
        ACTIVE = true;
        LOGGER.info("Replacing {} with a cached multipart check", mixinClassName);
        return true;
    }
}
