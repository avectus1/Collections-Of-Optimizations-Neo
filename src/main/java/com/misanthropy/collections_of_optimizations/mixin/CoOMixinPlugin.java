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
    private static final String LOOTR_MIXIN =
            "com.misanthropy.collections_of_optimizations.mixin.lootr.MixinBlockEntityTicker";
    private static final String PATCHOULI_MIXIN =
            "com.misanthropy.collections_of_optimizations.mixin.patchouli.MixinItemStackUtil";
    private static final String FTBCHUNKS_MIXIN =
            "com.misanthropy.collections_of_optimizations.mixin.ftbchunks.MixinFTBChunksClient";

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
        if (LOOTR_MIXIN.equals(mixinClassName)) {
            return LoadingModList.get().getModFileById("lootr") != null;
        }
        if (PATCHOULI_MIXIN.equals(mixinClassName)) {
            return LoadingModList.get().getModFileById("patchouli") != null;
        }
        if (FTBCHUNKS_MIXIN.equals(mixinClassName)) {
            return LoadingModList.get().getModFileById("ftbchunks") != null;
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
