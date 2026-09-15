package com.misanthropy.collections_of_optimizations.mixin;

import net.neoforged.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public final class CoOMixinPlugin implements IMixinConfigPlugin {
    private static final String XAERO_MIXIN =
            "com.misanthropy.collections_of_optimizations.mixin.xaerominimap.MixinMinimapFBORenderer";
    private static final String XAERO_WORLD_MAP_MIXIN =
            "com.misanthropy.collections_of_optimizations.mixin.xaeroworldmap.MixinMapLimiter";
    private static final String LOOTR_MIXIN =
            "com.misanthropy.collections_of_optimizations.mixin.lootr.MixinBlockEntityTicker";
    private static final String PATCHOULI_MIXIN =
            "com.misanthropy.collections_of_optimizations.mixin.patchouli.MixinItemStackUtil";
    private static final String AMBIENTSOUNDS_PREFIX =
            "com.misanthropy.collections_of_optimizations.mixin.ambientsounds.";
    private static final String BALM_PREFIX =
            "com.misanthropy.collections_of_optimizations.mixin.balm.";
    private static final String CAELUS_PREFIX =
            "com.misanthropy.collections_of_optimizations.mixin.caelus.";
    private static final String FTBCHUNKS_PREFIX =
            "com.misanthropy.collections_of_optimizations.mixin.ftbchunks.";
    private static final String CURIOS_PREFIX =
            "com.misanthropy.collections_of_optimizations.mixin.curios.";
    private static final String TERRABLENDER_PREFIX =
            "com.misanthropy.collections_of_optimizations.mixin.terrablender.";
    private static final String ZETA_PREFIX =
            "com.misanthropy.collections_of_optimizations.mixin.zeta.";

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (XAERO_MIXIN.equals(mixinClassName)) {
            return LoadingModList.get().getModFileById("xaerominimap") != null;
        }
        if (XAERO_WORLD_MAP_MIXIN.equals(mixinClassName)) {
            return LoadingModList.get().getModFileById("xaeroworldmap") != null;
        }
        if (LOOTR_MIXIN.equals(mixinClassName)) {
            return LoadingModList.get().getModFileById("lootr") != null;
        }
        if (PATCHOULI_MIXIN.equals(mixinClassName)) {
            return LoadingModList.get().getModFileById("patchouli") != null;
        }
        if (mixinClassName.startsWith(AMBIENTSOUNDS_PREFIX)) {
            return LoadingModList.get().getModFileById("ambientsounds") != null;
        }
        if (mixinClassName.startsWith(BALM_PREFIX)) {
            return LoadingModList.get().getModFileById("balm") != null;
        }
        if (mixinClassName.startsWith(CAELUS_PREFIX)) {
            return LoadingModList.get().getModFileById("caelus") != null;
        }
        if (mixinClassName.startsWith(FTBCHUNKS_PREFIX)) {
            return LoadingModList.get().getModFileById("ftbchunks") != null;
        }
        if (mixinClassName.startsWith(CURIOS_PREFIX)) {
            return LoadingModList.get().getModFileById("curios") != null;
        }
        if (mixinClassName.startsWith(TERRABLENDER_PREFIX)) {
            return LoadingModList.get().getModFileById("terrablender") != null;
        }
        if (mixinClassName.startsWith(ZETA_PREFIX)) {
            return LoadingModList.get().getModFileById("zeta") != null;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
