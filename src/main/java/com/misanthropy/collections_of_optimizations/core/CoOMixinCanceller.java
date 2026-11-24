package com.misanthropy.collections_of_optimizations.core;

import com.bawnorton.mixinsquared.api.MixinCanceller;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Swaps some other mods' mixins for leaner CoO versions that do the same thing. Each target is only cancelled
 * when the CoO replacement passes the same checks CoOMixinPlugin uses, so the two always agree.
 * -Dcoo.keepMixin.&lt;simple name&gt;=true keeps one mod's original mixin.
 */
public final class CoOMixinCanceller implements MixinCanceller {
    private static final Logger LOGGER = LoggerFactory.getLogger("collections_of_optimizations");
    private static final String P = "com.misanthropy.collections_of_optimizations.mixin.";

    /** mod mixin -> CoO mixin that replaces it (or a CoO group name for mixins that are simply dropped). */
    private static final Map<String, String> REPLACEMENTS = Map.of(
            "com.cerbon.cerbons_api.mixin.multipart_entities.EntityMixin", P + "cerbonsapi.MixinEntityMultipartHooks",
            "dev.obscuria.aquamirae.integration.mixin.MixinLevel", P + "aquamirae.MixinAquamiraeShellbackParts",
            "io.redspace.ironsspellbooks.mixin.NoiseChunkMixin", P + "ironsspellbooks.MixinIsbAquiferNuke",
            "com.craisinlord.antarchy.mixins.NoiseChunkGeneratorProfilerMixin", P + "antarchy.Profilers",
            "com.craisinlord.antarchy.mixins.ChunkGeneratorProfilerMixin", P + "antarchy.Profilers",
            "com.craisinlord.antarchy.mixins.StructureProfilerMixin", P + "antarchy.Profilers",
            "com.craisinlord.antarchy.mixins.PlacedFeatureProfilerMixin", P + "antarchy.Profilers",
            "com.craisinlord.antarchy.mixins.LevelChunkProfilerMixin", P + "antarchy.Profilers"
    );

    private static final Set<String> ACTIVE = ConcurrentHashMap.newKeySet();

    /** True once the given mod mixin has actually been cancelled in favour of ours. */
    public static boolean isActive(String modMixin) {
        return ACTIVE.contains(modMixin);
    }

    /** Whether the target would be cancelled; deterministic, so CoOMixinPlugin can ask before the canceller runs. */
    public static boolean wouldCancel(String modMixin) {
        String replacement = REPLACEMENTS.get(modMixin);
        if (replacement == null) {
            return false;
        }
        String simple = modMixin.substring(modMixin.lastIndexOf('.') + 1);
        if ("true".equals(System.getProperty("coo.keepMixin." + simple))) {
            return false;
        }
        return CoOMixinPlugin.wouldApply(replacement);
    }

    @Override
    public boolean shouldCancel(List<String> targetClassNames, String mixinClassName) {
        if (!wouldCancel(mixinClassName)) {
            return false;
        }
        if (ACTIVE.add(mixinClassName)) {
            LOGGER.info("Replacing {} with a leaner Collections of Optimizations version", mixinClassName);
        }
        return true;
    }
}
