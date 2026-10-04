package com.misanthropy.collections_of_optimizations.mixin;

import net.neoforged.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
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
    private static final String XAERO_WORLD_MAP_PREFIX =
            "com.misanthropy.collections_of_optimizations.mixin.xaeroworldmap.";
    private static final String ARCHITECTURY_PREFIX =
            "com.misanthropy.collections_of_optimizations.mixin.architectury.";
    private static final String ARTIFACTS_PREFIX =
            "com.misanthropy.collections_of_optimizations.mixin.artifacts.";
    private static final Map<String, String> A_F_MOD_IDS = Map.ofEntries(
            Map.entry("adastra", "ad_astra"),
            Map.entry("armageddon", "armageddon_mod"),
            Map.entry("arsadditions", "ars_additions"),
            Map.entry("arselemental", "ars_elemental"),
            Map.entry("arsnouveau", "ars_nouveau"),
            Map.entry("arsnspells", "ars_n_spells"),
            Map.entry("chimes", "chimes"),
            Map.entry("cataclysm", "cataclysm"),
            Map.entry("bettercombat", "bettercombat"),
            Map.entry("biolith", "biolith"),
            Map.entry("biomeswevegone", "biomeswevegone"),
            Map.entry("borninchaos", "born_in_chaos_v1"),
            Map.entry("cablefacades", "cable_facades"),
            Map.entry("copycats", "copycats"),
            Map.entry("create", "create"),
            Map.entry("createaddition", "createaddition"),
            Map.entry("cucumber", "cucumber"),
            Map.entry("corpse", "corpse"),
            Map.entry("dungeoncrawl", "dungeoncrawl"),
            Map.entry("dungeonsdelight", "dungeonsdelight"),
            Map.entry("easyvillagers", "easy_villagers"),
            Map.entry("emf", "entity_model_features"),
            Map.entry("farmersdelight", "farmersdelight"),
            Map.entry("fancymenu", "fancymenu"),
            Map.entry("crittersandcompanions", "crittersandcompanions")
    );

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
        if (mixinClassName.startsWith(XAERO_WORLD_MAP_PREFIX)) {
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
        if (mixinClassName.startsWith(ARCHITECTURY_PREFIX)) {
            return LoadingModList.get().getModFileById("architectury") != null;
        }
        if (mixinClassName.startsWith(ARTIFACTS_PREFIX)) {
            return LoadingModList.get().getModFileById("artifacts") != null;
        }
        String packagePrefix = "com.misanthropy.collections_of_optimizations.mixin.";
        if (mixinClassName.startsWith(packagePrefix)) {
            String remainder = mixinClassName.substring(packagePrefix.length());
            int separator = remainder.indexOf('.');
            if (separator > 0) {
                String modId = A_F_MOD_IDS.get(remainder.substring(0, separator));
                if (modId != null) {
                    return LoadingModList.get().getModFileById(modId) != null;
                }
            }
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
