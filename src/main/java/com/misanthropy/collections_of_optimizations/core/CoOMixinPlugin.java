package com.misanthropy.collections_of_optimizations.core;

import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
import net.neoforged.fml.loading.LoadingModList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CoOMixinPlugin implements IMixinConfigPlugin {

    private static final Logger LOGGER = LogManager.getLogger("collections_of_optimizations");
    private static final Set<String> WARNED_GROUPS = new HashSet<>();

    private static final String MIXIN_PACKAGE = "com.misanthropy.collections_of_optimizations.mixin.";
    private static final String ELYSIUM_SOUND_CACHE_MIXIN = MIXIN_PACKAGE + "elysiumapi.MixinBlockSoundTransformerCache";
    private static final String ELYSIUM_BLOCK_MIXIN = "net.jadenxgamer.elysium_api.impl.mixin.block.BlockMixin";
    private static final String ELYSIUM_HANDLER_DESC = "(Lnet/minecraft/world/level/block/state/BlockState;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V";
    private static final String ELYSIUM_DISPATCH_OWNER = "com/misanthropy/collections_of_optimizations/core/ElysiumSoundTransformers";
    private static final String MIXIN_MERGED_DESC = "Lorg/spongepowered/asm/mixin/transformer/meta/MixinMerged;";

    private static final Map<String, String> GROUP_TO_MODID = Map.ofEntries(
            Map.entry("adastra", "ad_astra"),
            Map.entry("alexscaves", "alexscaves"),
            Map.entry("alexsmobs", "alexsmobs"),
            Map.entry("alltheleaks", "alltheleaks"),
            Map.entry("ambientsounds", "ambientsounds"),
            Map.entry("architectury", "architectury"),
            Map.entry("armageddon", "armageddon_mod"),
            Map.entry("arsadditions", "ars_additions"),
            Map.entry("arselemental", "ars_elemental"),
            Map.entry("arsnouveau", "ars_nouveau"),
            Map.entry("artifacts", "artifacts"),
            Map.entry("asyncparticles", "asyncparticles"),
            Map.entry("balm", "balm"),
            Map.entry("bettercombat", "bettercombat"),
            Map.entry("biolith", "biolith"),
            Map.entry("biomeswevegone", "biomeswevegone"),
            Map.entry("bmd", "bosses_of_mass_destruction"),
            Map.entry("borninchaos", "born_in_chaos_v1"),
            Map.entry("cablefacades", "cable_facades"),
            Map.entry("caelus", "caelus"),
            Map.entry("cataclysm", "cataclysm"),
            Map.entry("chimes", "chimes"),
            Map.entry("citadel", "citadel"),
            Map.entry("copycats", "copycats"),
            Map.entry("corpse", "corpse"),
            Map.entry("create", "create"),
            Map.entry("createaddition", "createaddition"),
            Map.entry("cucumber", "cucumber"),
            Map.entry("curios", "curios"),
            Map.entry("deeperdarker", "deeperdarker"),
            Map.entry("dungeoncrawl", "dungeoncrawl"),
            Map.entry("dungeonsdelight", "dungeonsdelight"),
            Map.entry("duplicationless", "duplicationless"),
            Map.entry("easyvillagers", "easy_villagers"),
            Map.entry("eeeabsmobs", "eeeabsmobs"),
            Map.entry("elysiumapi", "elysium_api"),
            Map.entry("emf", "entity_model_features"),
            Map.entry("emi", "emi"),
            Map.entry("etf", "entity_texture_features"),
            Map.entry("fancymenu", "fancymenu"),
            Map.entry("farmersdelight", "farmersdelight"),
            Map.entry("fluidium", "fluidium"),
            Map.entry("ftbchunks", "ftbchunks"),
            Map.entry("ironsspellbooks", "irons_spellbooks"),
            Map.entry("legendarymonsters", "legendary_monsters"),
            Map.entry("lionfishapi", "lionfishapi"),
            Map.entry("lithostitched", "lithostitched"),
            Map.entry("lootr", "lootr"),
            Map.entry("modernfix", "modernfix"),
            Map.entry("netherexp", "netherexp"),
            Map.entry("notenoughanimations", "notenoughanimations"),
            Map.entry("patchouli", "patchouli"),
            Map.entry("placebo", "placebo"),
            Map.entry("regionsunexplored", "regions_unexplored"),
            Map.entry("sophisticatedbackpacks", "sophisticatedbackpacks"),
            Map.entry("terrablender", "terrablender"),
            Map.entry("xaerolib", "xaerolib"),
            Map.entry("xaerominimap", "xaerominimap"),
            Map.entry("xaeroworldmap", "xaeroworldmap"),
            Map.entry("amendments", "amendments"),
            Map.entry("dummmmmmy", "dummmmmmy"),
            Map.entry("immediatelyfast", "immediatelyfast"),
            Map.entry("moonlight", "moonlight"),
            Map.entry("shouldersurfing", "shouldersurfing"),
            Map.entry("sliceanddice", "sliceanddice"),
            Map.entry("structurify", "structurify"),
            Map.entry("supplementaries", "supplementaries"),
            Map.entry("chunksending", "chunksending"),
            Map.entry("illusioner", "illusioner"),
            Map.entry("iceberg", "iceberg"),
            Map.entry("lodestone", "lodestone"),
            Map.entry("kubejs", "kubejs"),
            Map.entry("doespotatotick", "doespotatotick"),
            Map.entry("structureessentials", "structureessentials"),
            Map.entry("gnetum", "gnetum"),
            Map.entry("flywheel", "flywheel"),
            Map.entry("byepregen", "byepregen"),
            Map.entry("ironslib", "irons_lib"),
            Map.entry("guardvillagers", "guardvillagers"),
            Map.entry("subtleeffects", "subtle_effects"),
            Map.entry("undergarden", "undergarden"),
            Map.entry("perception", "perception"),
            Map.entry("geckolib", "geckolib"),
            Map.entry("mowziesmobs", "mowziesmobs"),
            Map.entry("emiloot", "emi_loot"),
            Map.entry("toomanyrecipeviewers", "toomanyrecipeviewers"),
            Map.entry("cerbonsapi", "cerbons_api"),
            Map.entry("konkrete", "konkrete"),
            Map.entry("soundphysics", "sound_physics_remastered"),
            Map.entry("malum", "malum"),
            Map.entry("eidolon", "eidolon_repraised"),
            Map.entry("aquamirae", "aquamirae"),
            Map.entry("enderscape", "enderscape"),
            Map.entry("antarchy", "antarchy"),
            Map.entry("species", "species"),
            Map.entry("stutterfix", "stutterfix"),
            Map.entry("morequesttypes", "more_quest_types"),
            Map.entry("dungeonnowloading", "dungeonnowloading"),
            Map.entry("redomesticate", "redomesticate"),
            Map.entry("majruszsdifficulty", "majruszsdifficulty"),
            Map.entry("zeta", "zeta")
    );

    private static final Map<String, Integer> GROUP_MIN_MAJOR = Map.of();

    private static final Map<String, String> GROUP_EXACT_VERSION = Map.of();

    private static final Map<String, String[]> MIXIN_EXACT_VERSION = Map.of();

    private static final Map<String, String[]> GROUP_CONFLICTS = Map.of(
            "geckolib", new String[]{"gbf"},

            "iafdragonfix", new String[]{"iafdragonfix"}
    );

    private static final Map<String, String[]> MIXIN_CONFLICTS = Map.ofEntries(
            Map.entry("vanilla.MixinBeardifierReach", new String[]{"generator_accelerator", "c2me_opts_worldgen_vanilla"}),
            Map.entry("immersiveweathering.MixinAreaCheck", new String[]{"iwt"}),
            Map.entry("immersiveweathering.MixinBlockGrowthHandler", new String[]{"iwt"}),
            Map.entry("vanilla.MixinLevelDryRaycast", new String[]{"valkyrienskies"}),
            Map.entry("terrablender.MixinNamespacedSourceLazy", new String[]{"c2me"}),
            Map.entry("elysiumapi.MixinMultiNoiseBiomeSourceReplacers", new String[]{"terrablender"}),


            Map.entry("vanilla.MixinStringRenderOutputFontSet", new String[]{"immediatelyfast"}),
            Map.entry("vanilla.MixinRenderTargetLeakFree", new String[]{"gpumemleakfix"}),
            Map.entry("vanilla.MixinEntityFireScanGate", new String[]{"lithium"}),
            Map.entry("vanilla.MixinGameEventDispatcherLookup", new String[]{"lithium"}),
            Map.entry("vanilla.MixinStructureCheck", new String[]{"modernfix"}),
            Map.entry("vanilla.MixinServerLevelStructureState", new String[]{"modernfix"}),

            Map.entry("vanilla.MixinItemEntityRenderer", new String[]{"flerovium"}),

            Map.entry("geckolib.MixinAnimationProcessorPoseHold", new String[]{"geckolib_animation_optimizer"}),

            Map.entry("vanilla.MixinChunkMapEntityTracker", new String[]{"vmp", "hariplayer"}),

            Map.entry("vanilla.MixinClientLevelBiomeBlend", new String[]{"betterbiomeblend", "betterbiomereblend"}),

            Map.entry("vanilla.MixinPoseStackAxisRotation", new String[]{"lomka"}),
            Map.entry("vanilla.MixinBlockPosBetweenClosed", new String[]{"lomka"}),
            Map.entry("vanilla.MixinPalettedContainerUniform", new String[]{"lomka"})
    );

    private static final Map<String, Set<String>> INCOMPATIBLE_MODS = Map.of();

    private static final String[] TAKES_EVERYTHING = {"tickweave"};

    private static final Map<String, String[]> MIXIN_REQUIRES = Map.ofEntries(
            Map.entry("elysiumapi.MixinElysiumTerrablenderHelper", new String[]{"terrablender"}),
            Map.entry("species.MixinSpeciesGbfBoneRotation", new String[]{"species", "gbf"}),

            Map.entry("goetyrevelation.MixinATAHelper", new String[]{"curios"}),

            Map.entry("goety.MixinCuriosFinder", new String[]{"curios"}),

            Map.entry("goety.MixinCuriosFinderFilter", new String[]{"curios"}),

            Map.entry("morerelics.MixinMoreRelicsUtil", new String[]{"curios", "relics"}),

            Map.entry("modernfix.MixinJEIRuntimeCapturer", new String[]{"jei"}),

            Map.entry("w2w2.MixinXaeronCompatibility", new String[]{"xaerominimap"}),

            Map.entry("summonity.MixinSoloistsSealItem", new String[]{"curios"}),

            Map.entry("summonity.MixinPolychromicNecklaceItem", new String[]{"curios"}),

            Map.entry("summonity.MixinConductiveBatteryItem", new String[]{"curios"}),

            Map.entry("enigmaticlegacy.MixinSuperpositionCurioLookup", new String[]{"curios"}),

            Map.entry("lethality.MixinLethalityCharmScan", new String[]{"curios"}),

            Map.entry("castercuriosbonus.MixinCcbCurioLookup", new String[]{"curios"}),

            Map.entry("legendaryspellbooks.MixinLsTornadoTracker", new String[]{"legendary_monsters"}),

            Map.entry("legendaryspellbooks.MixinLsTornadoScan", new String[]{"legendary_monsters"}),

            Map.entry("meetyourfight.MixinMyfCurioLookup", new String[]{"curios"}),

            Map.entry("meetyourfight.MixinMyfSpectresEye", new String[]{"curios"}),

            Map.entry("copycats.MixinBlockRendererColorContext", new String[]{"sodium"}),

            Map.entry("citadel.MixinAdvancedModelBoxFastRender", new String[]{"sodium"}),

            Map.entry("citadel.MixinBasicModelPartFastRender", new String[]{"sodium"}),

            Map.entry("lionfishapi.MixinLionfishModelBoxRender", new String[]{"sodium"}),

            Map.entry("ironsspellbooks.MixinIsbCurioEquipCheck", new String[]{"curios"}),

            Map.entry("l2hostility.MixinCurioCompatHasItem", new String[]{"curios"})
    );

    private static final Map<String, String[]> MIXIN_ANY_OF = Map.of(
            "elysiumapi.MixinClimateSampler", new String[]{"elysium_api", "alexscaves"},
            "vanilla.MixinParticleEngineTextBatch", new String[]{"dummmmmmy", "blessfulled"}
    );

    /** CoO mixins that stand in for another mod's mixin and would clash with it if that one isn't removed. */
    private static final Set<String> NEEDS_MIXIN_CANCELLER = Set.of(
            "ironsspellbooks.MixinIsbAquiferNuke",
            "aquamirae.MixinAquamiraeShellbackParts",
            "cerbonsapi.MixinEntityMultipartHooks",
            "antarchy.Profilers",
            "species.MixinSpeciesGbfBoneRotation"
    );
    private static Boolean squaredPresent;

    private static synchronized boolean mixinSquaredPresent() {
        if (squaredPresent == null) {
            boolean found;
            try {
                found = org.spongepowered.asm.service.MixinService.getService().getBytecodeProvider()
                        .getClassNode("com.bawnorton.mixinsquared.api.MixinCanceller") != null;
            } catch (Throwable throwable) {
                found = false;
            }
            squaredPresent = found;
            if (!found) {
                LOGGER.info("MixinSquared not found; Collections of Optimizations won't replace other mods' mixins");
            }
        }
        return squaredPresent;
    }

    private static final Map<String, Boolean> LOADED_CACHE = new HashMap<>();
    private static final Map<String, Integer> MAJOR_CACHE = new HashMap<>();
    private static final Map<String, String> VERSION_CACHE = new HashMap<>();

    /** True when a mod that forces every CoO patch off is installed. */
    public static boolean allPatchesDisabled() {
        for (String modId : TAKES_EVERYTHING) {
            if (isModPresent(modId)) {
                return true;
            }
        }
        return false;
    }

    /** Whether {@link #shouldApplyMixin} would let this CoO mixin through, for cancellers that swap a mod's mixin for ours. */
    public static boolean wouldApply(String mixinClassName) {
        try {
            return new CoOMixinPlugin().shouldApplyMixin("", mixinClassName);
        } catch (Throwable throwable) {
            return false;
        }
    }

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!mixinClassName.startsWith(MIXIN_PACKAGE)) {
            return true;
        }
        String remainder = mixinClassName.substring(MIXIN_PACKAGE.length());
        int separator = remainder.indexOf('.');
        if (separator < 0) {
            return true;
        }
        String group = remainder.substring(0, separator);
        for (String modId : TAKES_EVERYTHING) {
            if (isModPresent(modId)) {
                if (WARNED_GROUPS.add("incompatible:" + modId)) {
                    LOGGER.warn("Disabling every Collections of Optimizations patch: '{}' runs entity ticks on worker threads and its own mixins are mandatory, so none of these patches can be applied safely next to it", modId);
                }
                return false;
            }
        }
        for (Map.Entry<String, Set<String>> incompatible : INCOMPATIBLE_MODS.entrySet()) {
            if (incompatible.getValue().contains(remainder) && isModPresent(incompatible.getKey())) {
                if (WARNED_GROUPS.add("incompatible:" + incompatible.getKey())) {
                    LOGGER.warn("Disabling the Collections of Optimizations patches that overlap '{}', it patches the same code", incompatible.getKey());
                }
                return false;
            }
        }
        String[] anyOf = MIXIN_ANY_OF.get(remainder);
        if (anyOf != null) {
            boolean found = false;
            for (String candidate : anyOf) {
                if (isModPresent(candidate)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        } else {
            String modId = GROUP_TO_MODID.get(group);
            if (modId != null && !isModPresent(modId)) {
                return false;
            }
            Integer minMajor = GROUP_MIN_MAJOR.get(group);
            if (modId != null && minMajor != null && majorVersion(modId) < minMajor) {
                if (WARNED_GROUPS.add(group)) {
                    LOGGER.warn("Disabling '{}' optimizations: installed '{}' version is unsupported{}",
                            group, modId,
                            "iceandfire".equals(modId)
                                    ? " (Ice and Fire: Community Edition is not supported - use the original Ice and Fire mod to enable these optimizations)"
                                    : "");
                }
                return false;
            }
            String exact = GROUP_EXACT_VERSION.get(group);
            if (modId != null && exact != null && !exact.equals(modVersion(modId))) {
                if (WARNED_GROUPS.add(group)) {
                    LOGGER.warn("Disabling '{}' optimizations: they were verified against '{}' {} but {} is installed",
                            group, modId, exact, modVersion(modId));
                }
                return false;
            }
        }
        String[] conflicts = GROUP_CONFLICTS.get(group);
        if (conflicts != null) {
            for (String conflict : conflicts) {
                if (isModPresent(conflict)) {
                    return false;
                }
            }
        }
        String[] mixinConflicts = MIXIN_CONFLICTS.get(remainder);
        if (mixinConflicts != null) {
            for (String conflict : mixinConflicts) {
                if (isModPresent(conflict)) {
                    return false;
                }
            }
        }
        String[] pinned = MIXIN_EXACT_VERSION.get(remainder);
        if (pinned != null && !pinned[1].equals(modVersion(pinned[0]))) {
            return false;
        }
        if (NEEDS_MIXIN_CANCELLER.contains(remainder) && !mixinSquaredPresent()) {
            return false;
        }
        String[] required = MIXIN_REQUIRES.get(remainder);
        if (required != null) {
            for (String dependency : required) {
                if (!isModPresent(dependency)) {
                    return false;
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
        if (ELYSIUM_SOUND_CACHE_MIXIN.equals(mixinClassName)) {
            rewireElysiumSoundHandler(targetClass);
        }
        if (mixinClassName.endsWith("HugeMethods") && !"false".equals(System.getProperty("coo.splitHugeMethods"))) {
            HugeMethodSplitter.split(targetClass);
        }
    }

    private static void rewireElysiumSoundHandler(ClassNode target) {
        String handler = null;
        for (MethodNode method : target.methods) {
            if ((method.access & Opcodes.ACC_STATIC) == 0 && ELYSIUM_HANDLER_DESC.equals(method.desc)
                    && mergedFrom(method, ELYSIUM_BLOCK_MIXIN)) {
                handler = method.name;
                method.access = (method.access & ~(Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED)) | Opcodes.ACC_PUBLIC;
                break;
            }
        }
        if (handler == null) {
            LOGGER.info("Collections of Optimizations: Elysium sound handler not found in {}, cache stays off", target.name);
            return;
        }
        List<MethodInsnNode> calls = new ArrayList<>();
        for (MethodNode method : target.methods) {
            for (AbstractInsnNode insn : method.instructions) {
                if (insn instanceof MethodInsnNode call && call.owner.equals(target.name) && call.name.equals(handler)
                        && call.desc.equals(ELYSIUM_HANDLER_DESC)
                        && (call.getOpcode() == Opcodes.INVOKEVIRTUAL || call.getOpcode() == Opcodes.INVOKESPECIAL)) {
                    calls.add(call);
                }
            }
        }
        for (MethodNode method : target.methods) {
            for (MethodInsnNode call : calls) {
                if (method.instructions.contains(call)) {
                    method.instructions.set(call, new MethodInsnNode(Opcodes.INVOKESTATIC, ELYSIUM_DISPATCH_OWNER, "dispatch",
                            "(L" + target.name + ";" + ELYSIUM_HANDLER_DESC.substring(1), false));
                }
            }
        }
        LOGGER.info("Collections of Optimizations: routed {} Elysium sound handler call(s) through the cache", calls.size());
    }

    private static boolean mergedFrom(MethodNode method, String mixin) {
        return mergedFrom(method.visibleAnnotations, mixin) || mergedFrom(method.invisibleAnnotations, mixin);
    }

    private static boolean mergedFrom(List<AnnotationNode> annotations, String mixin) {
        if (annotations == null) {
            return false;
        }
        for (AnnotationNode annotation : annotations) {
            if (MIXIN_MERGED_DESC.equals(annotation.desc) && annotation.values != null) {
                for (int i = 0; i + 1 < annotation.values.size(); i += 2) {
                    if ("mixin".equals(annotation.values.get(i)) && mixin.equals(annotation.values.get(i + 1))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static synchronized boolean isModPresent(String modId) {
        Boolean cached = LOADED_CACHE.get(modId);
        if (cached != null) {
            return cached;
        }
        boolean present;
        try {
            present = LoadingModList.get().getModFileById(modId) != null;
        } catch (Throwable throwable) {
            present = false;
        }
        LOADED_CACHE.put(modId, present);
        return present;
    }

    private static synchronized int majorVersion(String modId) {
        Integer cached = MAJOR_CACHE.get(modId);
        if (cached != null) {
            return cached;
        }
        int major = Integer.MAX_VALUE;
        String version = modVersion(modId);
        int end = 0;
        while (end < version.length() && Character.isDigit(version.charAt(end))) {
            end++;
        }
        if (end > 0) {
            major = Integer.parseInt(version.substring(0, Math.min(end, 9)));
        }
        MAJOR_CACHE.put(modId, major);
        return major;
    }

    private static synchronized String modVersion(String modId) {
        String cached = VERSION_CACHE.get(modId);
        if (cached != null) {
            return cached;
        }
        String version = "";
        try {
            ModFileInfo modFile = LoadingModList.get().getModFileById(modId);
            if (modFile != null) {
                for (IModInfo mod : modFile.getMods()) {
                    if (mod.getModId().equals(modId)) {
                        version = mod.getVersion().toString();
                        break;
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        VERSION_CACHE.put(modId, version);
        return version;
    }
}
