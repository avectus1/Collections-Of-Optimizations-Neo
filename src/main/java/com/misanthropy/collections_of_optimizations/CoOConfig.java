package com.misanthropy.collections_of_optimizations;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.event.config.ModConfigEvent;

public final class CoOConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.BooleanValue MASTER_ENABLED;
    public static final ModConfigSpec.BooleanValue ARCHITECTURY_DIRECT_EVENT_DISPATCH;
    public static final ModConfigSpec.BooleanValue ARTIFACTS_SKIP_CLIENT_TICK_ON_NON_PLAYERS;
    public static final ModConfigSpec.IntValue XAERO_MINIMAP_RENDER_FPS_CAP;
    public static final ModConfigSpec.IntValue XAERO_WORLD_MAP_VRAM_POLL_INTERVAL;
    public static final ModConfigSpec.IntValue XAERO_WORLD_MAP_RENDER_PROCESS_INTERVAL;
    public static final ModConfigSpec.BooleanValue XAERO_WORLD_MAP_IDLE_MAP_FRAME_WAIT;
    public static final ModConfigSpec.IntValue XAERO_WORLD_MAP_MAP_FRAME_SPIN_TAIL;
    public static final ModConfigSpec.IntValue XAERO_WORLD_MAP_UPLOAD_HEADROOM_MICROS;
    public static final ModConfigSpec.BooleanValue XAERO_WORLD_MAP_ORPHAN_UPLOAD_PBO;
    public static final ModConfigSpec.BooleanValue XAERO_WORLD_MAP_OPEN_REFLECTED_FIELDS;
    public static final ModConfigSpec.BooleanValue LOOTR_SKIP_IDLE_BLOCK_ENTITY_TICKER;
    public static final ModConfigSpec.IntValue LOOTR_BLOCK_ENTITY_TICKER_BUDGET;
    public static final ModConfigSpec.BooleanValue PATCHOULI_CACHE_BOOK_ITEM_LOOKUP;
    public static final ModConfigSpec.BooleanValue FTBCHUNKS_SKIP_HIDDEN_MINIMAP_WORK;
    public static final ModConfigSpec.BooleanValue FTBCHUNKS_FAST_REGION_WRITE;
    public static final ModConfigSpec.BooleanValue FTBCHUNKS_DISABLE_MAP_WRITING;
    public static final ModConfigSpec.BooleanValue CURIOS_SKIP_SLOTLESS_ENTITIES;
    public static final ModConfigSpec.BooleanValue CURIOS_SKIP_CLIENT_TICK_ON_NON_PLAYERS;
    public static final ModConfigSpec.BooleanValue CURIOS_CACHE_ENTITY_SLOT_LOOKUP;
    public static final ModConfigSpec.BooleanValue CURIOS_FAST_EQUIPPED_ITEM_MISS;
    public static final ModConfigSpec.BooleanValue CURIOS_FAST_FIND_FIRST_MISS;
    public static final ModConfigSpec.BooleanValue CURIOS_REUSE_MAP_VIEW;
    public static final ModConfigSpec.BooleanValue CURIOS_RECONCILE_CACHED_SLOT_MODIFIERS;
    public static final ModConfigSpec.BooleanValue CURIOS_THREAD_SAFE_CACHES;
    public static final ModConfigSpec.BooleanValue TERRABLENDER_CACHE_NAMESPACE_RULE;
    public static final ModConfigSpec.BooleanValue TERRABLENDER_THREAD_LOCAL_AREA_CACHE;
    public static final ModConfigSpec.BooleanValue ZETA_LEAN_STRUCTURE_REPLACEMENT;
    public static final ModConfigSpec.BooleanValue ZETA_SHARE_EVENT_WRAPPERS;
    public static final ModConfigSpec.BooleanValue AMBIENTSOUNDS_MEMO_BIOME_MATCH;
    public static final ModConfigSpec.BooleanValue BALM_MEMO_DYNAMIC_MODEL_KEYS;
    public static final ModConfigSpec.BooleanValue BALM_CACHE_CONFIG_IDENTIFIER;
    public static final ModConfigSpec.BooleanValue CAELUS_SKIP_GROUNDED_NON_PLAYERS;

    public static final ModConfigSpec.BooleanValue ADASTRA_MEMO_PLANET_DEFAULTS;
    public static final ModConfigSpec.BooleanValue ARMAGEDDON_CACHE_PROGRESSION_IDS;
    public static final ModConfigSpec.BooleanValue ARMAGEDDON_SKIP_FOREIGN_ENTITY_ANIMATIONS;
    public static final ModConfigSpec.BooleanValue ARSADDITIONS_CACHE_RUINED_PORTAL_SCAN;
    public static final ModConfigSpec.IntValue ARSADDITIONS_MAGELIGHT_PARTICLE_DISTANCE;
    public static final ModConfigSpec.BooleanValue ARSADDITIONS_SKIP_STOWED_CHARM_TICK;
    public static final ModConfigSpec.IntValue ARSNOUVEAU_SKY_TEXTURE_INTERVAL;
    public static final ModConfigSpec.BooleanValue ARSNSPELLS_CARRY_COOLDOWNS_THROUGH_CLONE;
    public static final ModConfigSpec.BooleanValue ARSNSPELLS_LEAN_MANA_BAR_OVERLAY_GATE;
    public static final ModConfigSpec.BooleanValue ARSNSPELLS_LEAN_RESONANCE_TICK;
    public static final ModConfigSpec.BooleanValue ARSNSPELLS_LEAN_SOURCE_JAR_TICK;
    public static final ModConfigSpec.BooleanValue BETTERCOMBAT_CACHE_WEAPON_ATTRIBUTES;
    public static final ModConfigSpec.BooleanValue BETTERCOMBAT_OFFHAND_ONLY_TWO_HANDED_CHECK;
    public static final ModConfigSpec.BooleanValue BIOLITH_EARLY_REGISTRY_CAPTURE;
    public static final ModConfigSpec.BooleanValue BIOLITH_RESTAMP_SWAPPED_BIOME_SOURCE;
    public static final ModConfigSpec.BooleanValue BIOLITH_REUSE_BIOME_ENTRIES;
    public static final ModConfigSpec.BooleanValue COPYCATS_CACHED_MODEL_CONFIG;
    public static final ModConfigSpec.BooleanValue COPYCATS_FAST_MIGRATION_CHECKS;
    public static final ModConfigSpec.BooleanValue COPYCATS_LEAN_VIRTUAL_WORLD_CHECK;
    public static final ModConfigSpec.BooleanValue COPYCATS_MEMO_STATE_OCCLUSION;
    public static final ModConfigSpec.IntValue CORPSE_DEATH_SWEEP_INTERVAL;
    public static final ModConfigSpec.IntValue CORPSE_IDLE_SCAN_INTERVAL;
    public static final ModConfigSpec.BooleanValue CREATEADDITION_FIX_AMBIENT_SOUND_TOGGLE;
    public static final ModConfigSpec.BooleanValue CREATEADDITION_LEAN_HELD_WIRE_CHECK;
    public static final ModConfigSpec.BooleanValue CREATEADDITION_SOUNDSCAPE_TICK_ONCE;
    public static final ModConfigSpec.BooleanValue CREATE_DEDUPE_BIG_OUTLINE_PROBES;
    public static final ModConfigSpec.IntValue CREATE_BIG_OUTLINE_PICK_INTERVAL;
    public static final ModConfigSpec.BooleanValue CUCUMBER_CACHE_TAG_LOOKUP;
    public static final ModConfigSpec.BooleanValue CUCUMBER_LEAN_TAG_TOOLTIP;
    public static final ModConfigSpec.BooleanValue CUCUMBER_LEAN_TILE_DISPATCH;
    public static final ModConfigSpec.BooleanValue DUNGEONCRAWL_SKIP_BLOCK_ENTITY_PROBE;
    public static final ModConfigSpec.BooleanValue DUNGEONSDELIGHT_LEAN_MONSTER_EFFECT_SWAP;
    public static final ModConfigSpec.BooleanValue DUNGEONSDELIGHT_LEAN_YAM_AURA;
    public static final ModConfigSpec.BooleanValue DUNGEONSDELIGHT_NO_DUPLICATE_ZOMBIE_ADD;
    public static final ModConfigSpec.BooleanValue DUNGEONSDELIGHT_PER_PLAYER_TENACITY_INTERVAL;
    public static final ModConfigSpec.BooleanValue DUNGEONSDELIGHT_SHARED_FERAL_BITE_RANDOM;
    public static final ModConfigSpec.BooleanValue DUNGEONSDELIGHT_SKIP_INVISIBLE_RUSH_OVERLAY;
    public static final ModConfigSpec.IntValue EASYVILLAGERS_DIRTY_MARK_INTERVAL;
    public static final ModConfigSpec.BooleanValue EMF_CACHE_BLOCK_ENTITY_TYPE_NAME;
    public static final ModConfigSpec.BooleanValue EMF_CACHE_NBT_PER_TICK;
    public static final ModConfigSpec.BooleanValue EMF_DROP_ZERO_ANGER_ENTRIES;
    public static final ModConfigSpec.BooleanValue FANCYMENU_CACHE_SCREEN_BLACKLIST;
    public static final ModConfigSpec.BooleanValue FANCYMENU_CACHE_SCREEN_IDENTIFIERS;
    public static final ModConfigSpec.IntValue FANCYMENU_INTERNET_PROBE_SECONDS;
    public static final ModConfigSpec.BooleanValue FANCYMENU_PIN_RENDER_STATE_TO_RENDER_THREAD;
    public static final ModConfigSpec.IntValue FANCYMENU_SEAMLESS_CAPTURE_INTERVAL;
    public static final ModConfigSpec.BooleanValue FANCYMENU_SKIP_REDUNDANT_SCALE_WRITES;
    public static final ModConfigSpec.BooleanValue FANCYMENU_TRACK_POSE_STACK_ONLY_IN_SCREENS;
    public static final ModConfigSpec.BooleanValue FARMERSDELIGHT_CACHE_OVERLAY_LOOKUP;
    public static final ModConfigSpec.BooleanValue FARMERSDELIGHT_LEAN_BASKET_SCAN;
    public static final ModConfigSpec.BooleanValue FARMERSDELIGHT_LEAN_TEMPT_GOAL_PRIORITY;
    public static final ModConfigSpec.BooleanValue FARMERSDELIGHT_SKIP_CLIENT_TEMPT_GOALS;
    public static final ModConfigSpec.BooleanValue FARMERSDELIGHT_SKIP_EMPTY_STOVE_WORK;
    public static final ModConfigSpec.BooleanValue FARMERSDELIGHT_SKIP_NOOP_ITEM_SYNC;
    public static final ModConfigSpec.BooleanValue CHIMES_SKIP_CLIENT_PHANTOM_SCAN;
    public static final ModConfigSpec.IntValue CHIMES_PHANTOM_SCAN_INTERVAL;
    public static final ModConfigSpec.BooleanValue CATACLYSM_CACHE_SHAKE_SCAN;
    public static final ModConfigSpec.BooleanValue BIOMESWEVEGONE_SKIP_FOREIGN_CHUNK_TERRAIN;
    public static final ModConfigSpec.BooleanValue BORNINCHAOS_SKIP_ITEM_ANIMATION_COPIES;
    public static final ModConfigSpec.BooleanValue BORNINCHAOS_SKIP_FOREIGN_ENTITY_ANIMATIONS;
    public static final ModConfigSpec.BooleanValue BORNINCHAOS_SKIP_REDUNDANT_DIMENSION_REFRESH;
    public static final ModConfigSpec.BooleanValue BORNINCHAOS_NARROW_MINION_SCANS;
    public static final ModConfigSpec.BooleanValue CABLEFACADES_SKIP_EMPTY_LOOKUP;
    public static final ModConfigSpec.BooleanValue ARSELEMENTAL_LEAN_CURIO_FALLBACK;

    public static volatile boolean masterEnabled;
    public static volatile boolean architecturyDirectEventDispatch;
    public static volatile boolean artifactsSkipClientTickOnNonPlayers;
    public static volatile int xaeroMinimapRenderFpsCap;
    public static volatile int xaeroworldmapVramPollInterval;
    public static volatile int xaeroworldmapRenderProcessInterval;
    public static volatile boolean xaeroworldmapIdleMapFrameWait;
    public static volatile int xaeroworldmapMapFrameSpinTail;
    public static volatile int xaeroworldmapUploadHeadroomMicros;
    public static volatile boolean xaeroworldmapOrphanUploadPbo;
    public static volatile boolean xaeroworldmapOpenReflectedFields;
    public static volatile boolean lootrSkipIdleBlockEntityTicker;
    public static volatile int lootrBlockEntityTickerBudget;
    public static volatile boolean patchouliCacheBookItemLookup;
    public static volatile boolean ftbchunksSkipHiddenMinimapWork;
    public static volatile boolean ftbchunksFastRegionWrite;
    public static volatile boolean ftbchunksDisableMapWriting;
    public static volatile boolean curiosSkipSlotlessEntities;
    public static volatile boolean curiosSkipClientTickOnNonPlayers;
    public static volatile boolean curiosCacheEntitySlotLookup;
    public static volatile boolean curiosFastEquippedItemMiss;
    public static volatile boolean curiosFastFindFirstMiss;
    public static volatile boolean curiosReuseMapView;
    public static volatile boolean curiosReconcileCachedSlotModifiers;
    public static volatile boolean curiosThreadSafeCaches = true;
    public static volatile boolean terrablenderCacheNamespaceRule;
    public static volatile boolean terrablenderThreadLocalAreaCache;
    public static volatile boolean zetaLeanStructureReplacement;
    public static volatile boolean zetaShareEventWrappers;
    public static volatile boolean ambientsoundsMemoBiomeMatch;
    public static volatile boolean balmMemoDynamicModelKeys;
    public static volatile boolean balmCacheConfigIdentifier;
    public static volatile boolean caelusSkipGroundedNonPlayers;

    // A-F reference targets; defaults mirror the audited reference configuration.
    public static volatile boolean adastraMemoPlanetDefaults = true;
    public static volatile boolean armageddonCacheProgressionIds = true;
    public static volatile boolean armageddonSkipForeignEntityAnimations = true;
    public static volatile boolean arsadditionsCacheRuinedPortalScan = true;
    public static volatile int arsadditionsMagelightParticleDistance = 32;
    public static volatile boolean arsadditionsSkipStowedCharmTick = true;
    public static volatile int arsnouveauSkyTextureInterval = 1;
    public static volatile boolean arsnspellsCarryCooldownsThroughClone = true;
    public static volatile boolean arsnspellsLeanManaBarOverlayGate = true;
    public static volatile boolean arsnspellsLeanResonanceTick = true;
    public static volatile boolean arsnspellsLeanSourceJarTick = true;
    public static volatile boolean bettercombatCacheWeaponAttributes = true;
    public static volatile boolean bettercombatOffhandOnlyTwoHandedCheck = true;
    public static volatile boolean biolithEarlyRegistryCapture = true;
    public static volatile boolean biolithRestampSwappedBiomeSource = true;
    public static volatile boolean biolithReuseBiomeEntries = true;
    public static volatile boolean copycatsCachedModelConfig = true;
    public static volatile boolean copycatsFastMigrationChecks = true;
    public static volatile boolean copycatsLeanVirtualWorldCheck = true;
    public static volatile boolean copycatsMemoStateOcclusion = true;
    public static volatile int corpseDeathSweepInterval = 300;
    public static volatile int corpseIdleScanInterval = 20;
    public static volatile boolean createadditionFixAmbientSoundToggle = true;
    public static volatile boolean createadditionLeanHeldWireCheck = true;
    public static volatile boolean createadditionSoundscapeTickOnce = true;
    public static volatile boolean createDedupeBigOutlineProbes = true;
    public static volatile int createBigOutlinePickInterval = 5;
    public static volatile boolean cucumberCacheTagLookup = true;
    public static volatile boolean cucumberLeanTagTooltip = true;
    public static volatile boolean cucumberLeanTileDispatch = true;
    public static volatile boolean dungeoncrawlSkipBlockEntityProbe = true;
    public static volatile boolean dungeonsdelightLeanMonsterEffectSwap = true;
    public static volatile boolean dungeonsdelightLeanYamAura = true;
    public static volatile boolean dungeonsdelightNoDuplicateZombieAdd = true;
    public static volatile boolean dungeonsdelightPerPlayerTenacityInterval = true;
    public static volatile boolean dungeonsdelightSharedFeralBiteRandom = true;
    public static volatile boolean dungeonsdelightSkipInvisibleRushOverlay = true;
    public static volatile int easyvillagersDirtyMarkInterval = 20;
    public static volatile boolean emfCacheBlockEntityTypeName = true;
    public static volatile boolean emfCacheNbtPerTick = true;
    public static volatile boolean emfDropZeroAngerEntries = true;
    public static volatile boolean fancymenuCacheScreenBlacklist = true;
    public static volatile boolean fancymenuCacheScreenIdentifiers = true;
    public static volatile int fancymenuInternetProbeSeconds = 600;
    public static volatile boolean fancymenuPinRenderStateToRenderThread = true;
    public static volatile int fancymenuSeamlessCaptureInterval = 30;
    public static volatile boolean fancymenuSkipRedundantScaleWrites = true;
    public static volatile boolean fancymenuTrackPoseStackOnlyInScreens = true;
    public static volatile boolean farmersdelightCacheOverlayLookup = true;
    public static volatile boolean farmersdelightLeanBasketScan = true;
    public static volatile boolean farmersdelightLeanTemptGoalPriority = true;
    public static volatile boolean farmersdelightSkipClientTemptGoals = true;
    public static volatile boolean farmersdelightSkipEmptyStoveWork = true;
    public static volatile boolean farmersdelightSkipNoopItemSync = true;
    public static volatile boolean chimesSkipClientPhantomScan = true;
    public static volatile int chimesPhantomScanInterval = 4;
    public static volatile boolean cataclysmCacheShakeScan = true;
    public static volatile boolean biomeswevegoneSkipForeignChunkTerrain = true;
    public static volatile boolean borninchaosSkipItemAnimationCopies = true;
    public static volatile boolean borninchaosSkipForeignEntityAnimations = true;
    public static volatile boolean borninchaosSkipRedundantDimensionRefresh = true;
    public static volatile boolean borninchaosNarrowMinionScans = true;
    public static volatile boolean cablefacadesSkipEmptyLookup = true;
    public static volatile boolean arselementalLeanCurioFallback = true;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        MASTER_ENABLED = builder
                .comment("Enable all Collections of Optimizations patches.")
                .define("masterEnabled", true);

        builder.comment("Architectury patches.").push("architectury");
        ARCHITECTURY_DIRECT_EVENT_DISPATCH = builder
                .comment("Use direct reflective event dispatch instead of constructing a MethodHandle for each call.")
                .define("directEventDispatch", true);
        builder.pop();

        builder.comment("Artifacts patches.").push("artifacts");
        ARTIFACTS_SKIP_CLIENT_TICK_ON_NON_PLAYERS = builder
                .comment("Skip Artifacts' client living tick for non-player entities.")
                .define("skipClientTickOnNonPlayers", true);
        builder.pop();

        builder.comment("Xaero's Minimap patches.").push("xaerominimap");
        XAERO_MINIMAP_RENDER_FPS_CAP = builder
                .comment("Maximum number of Minimap FBO renders per second. Set to 0 to disable the cap.")
                .defineInRange("renderFpsCap", 30, 0, 240);
        builder.pop();

        builder.comment("Xaero's World Map patches.").push("xaeroworldmap");
        XAERO_WORLD_MAP_VRAM_POLL_INTERVAL = builder
                .comment("Milliseconds between free-VRAM queries. Set to 0 to query every frame as stock does.")
                .defineInRange("vramPollInterval", 500, 0, 60000);
        XAERO_WORLD_MAP_RENDER_PROCESS_INTERVAL = builder
                .comment("Minimum milliseconds between world map render processing calls outside the map screen. Set to 0 to keep the stock rate.")
                .defineInRange("renderProcessInterval", 4, 0, 200);
        XAERO_WORLD_MAP_IDLE_MAP_FRAME_WAIT = builder
                .comment("Park while waiting for the world map frame budget instead of busy-spinning.")
                .define("idleMapFrameWait", true);
        XAERO_WORLD_MAP_MAP_FRAME_SPIN_TAIL = builder
                .comment("Microseconds to keep spinning at the end of the world map frame wait.")
                .defineInRange("mapFrameSpinTailMicros", 200, 0, 1600);
        XAERO_WORLD_MAP_UPLOAD_HEADROOM_MICROS = builder
                .comment("Microseconds reserved for other render work by the world map upload budget.")
                .defineInRange("uploadHeadroomMicros", 1000, 100, 3000);
        XAERO_WORLD_MAP_ORPHAN_UPLOAD_PBO = builder
                .comment("Orphan the world map pixel buffer before mapping it for a new upload.")
                .define("orphanUploadPbo", true);
        XAERO_WORLD_MAP_OPEN_REFLECTED_FIELDS = builder
                .comment("Open the world map reflection fields once instead of toggling accessibility on every read.")
                .define("openReflectedFields", true);
        builder.pop();

        builder.comment("Lootr patches.").push("lootr");
        LOOTR_SKIP_IDLE_BLOCK_ENTITY_TICKER = builder
                .comment("Skip Lootr's per-level conversion pass while both queues are empty.")
                .define("skipIdleBlockEntityTicker", true);
        LOOTR_BLOCK_ENTITY_TICKER_BUDGET = builder
                .comment("Maximum Lootr block-entity conversion candidates to process per server tick. Set to 0 for no limit.")
                .defineInRange("blockEntityTickerBudget", 512, 0, 65536);
        builder.pop();

        builder.comment("Patchouli patches.").push("patchouli");
        PATCHOULI_CACHE_BOOK_ITEM_LOOKUP = builder
                .comment("Cache Patchouli's lookup from an item to its guide book.")
                .define("cacheBookItemLookup", true);
        builder.pop();

        builder.comment("FTB Chunks patches.").push("ftbchunks");
        FTBCHUNKS_SKIP_HIDDEN_MINIMAP_WORK = builder
                .comment("Skip rebuilding the minimap texture when the minimap is not visible.")
                .define("skipHiddenMinimapWork", true);
        FTBCHUNKS_FAST_REGION_WRITE = builder
                .comment("Write map-region image pixels directly instead of calling BufferedImage.setRGB per pixel.")
                .define("fastRegionWrite", true);
        FTBCHUNKS_DISABLE_MAP_WRITING = builder
                .comment("Disable FTB Chunks minimap map-task writes and rerenders.")
                .define("disableMapWriting", false);
        builder.pop();

        builder.comment("Curios patches.").push("curios");
        CURIOS_SKIP_SLOTLESS_ENTITIES = builder
                .comment("Skip Curios ticking for entities that have no Curios slots.")
                .define("skipSlotlessEntities", true);
        CURIOS_SKIP_CLIENT_TICK_ON_NON_PLAYERS = builder
                .comment("Skip Curios client ticking for non-player entities.")
                .define("skipClientTickOnNonPlayers", true);
        CURIOS_CACHE_ENTITY_SLOT_LOOKUP = builder
                .comment("Cache whether an entity type has Curios slots.")
                .define("cacheEntitySlotLookup", true);
        CURIOS_FAST_EQUIPPED_ITEM_MISS = builder
                .comment("Avoid scanning Curios slots when an entity cannot contain the requested item.")
                .define("fastEquippedItemMiss", true);
        CURIOS_FAST_FIND_FIRST_MISS = builder
                .comment("Avoid scanning Curios slots for findFirstCurio when the requested item is absent.")
                .define("fastFindFirstMiss", true);
        CURIOS_REUSE_MAP_VIEW = builder
                .comment("Reuse the unmodifiable map view returned by Curios instead of allocating a wrapper per call.")
                .define("reuseMapView", true);
        CURIOS_RECONCILE_CACHED_SLOT_MODIFIERS = builder
                .comment("Keep restored slot modifiers that are still supplied by equipped Curios or armor, and avoid clearing unrelated slot caches.")
                .define("reconcileCachedSlotModifiers", true);
        CURIOS_THREAD_SAFE_CACHES = builder
                .comment("Use concurrent maps for Curios' shared slot-attribute and slot-UUID caches. This option takes effect when Curios first loads.")
                .define("threadSafeCaches", true);
        builder.pop();

        builder.comment("TerraBlender patches.").push("terrablender");
        TERRABLENDER_CACHE_NAMESPACE_RULE = builder
                .comment("Cache TerraBlender's namespace rule lookup for the current biome.")
                .define("cacheNamespaceRule", true);
        TERRABLENDER_THREAD_LOCAL_AREA_CACHE = builder
                .comment("Use a per-thread TerraBlender region cache to avoid taking its shared cache lock during world generation.")
                .define("threadLocalAreaCache", true);
        builder.pop();

        builder.comment("Zeta patches.").push("zeta");
        ZETA_LEAN_STRUCTURE_REPLACEMENT = builder
                .comment("Reuse a snapshot of Zeta structure replacement functions.")
                .define("leanStructureReplacement", true);
        ZETA_SHARE_EVENT_WRAPPERS = builder
                .comment("Reuse a Zeta event wrapper while dispatching the same Forge event.")
                .define("shareEventWrappers", true);
        builder.pop();

        builder.comment("AmbientSounds patches.").push("ambientsounds");
        AMBIENTSOUNDS_MEMO_BIOME_MATCH = builder
                .comment("Memoize repeated biome condition checks on the client thread.")
                .define("memoBiomeMatch", true);
        builder.pop();

        builder.comment("Balm patches.").push("balm");
        BALM_MEMO_DYNAMIC_MODEL_KEYS = builder
                .comment("Cache BlockState string keys used by Balm dynamic model lookup.")
                .define("memoDynamicModelKeys", true);
        BALM_CACHE_CONFIG_IDENTIFIER = builder
                .comment("Cache Balm config class-to-identifier reflection lookups.")
                .define("cacheConfigIdentifier", true);
        builder.pop();

        builder.comment("Caelus patches.").push("caelus");
        CAELUS_SKIP_GROUNDED_NON_PLAYERS = builder
                .comment("Skip Caelus flight checks for grounded non-player entities.")
                .define("skipGroundedNonPlayers", true);
        builder.pop();


        builder.comment("Ad Astra patches.").push("adastra");
        ADASTRA_MEMO_PLANET_DEFAULTS = builder
                .comment("Cache default planet values by dimension.")
                .define("memoPlanetDefaults", true);
        builder.pop();
        builder.comment("Armageddon patches.").push("armageddon");
        ARMAGEDDON_CACHE_PROGRESSION_IDS = builder
                .comment("Cache progression identifier parsing.")
                .define("cacheProgressionIds", true);
        ARMAGEDDON_SKIP_FOREIGN_ENTITY_ANIMATIONS = builder
                .comment("Skip animation work for entities outside Armageddon.")
                .define("skipForeignEntityAnimations", true);
        builder.pop();
        builder.comment("Ars Additions patches.").push("arsadditions");
        ARSADDITIONS_CACHE_RUINED_PORTAL_SCAN = builder
                .comment("Cache failed ruined portal structure scans.")
                .define("cacheRuinedPortalScan", true);
        ARSADDITIONS_MAGELIGHT_PARTICLE_DISTANCE = builder
                .comment("Maximum distance for Magelight lantern particles.")
                .defineInRange("magelightParticleDistance", 32, 0, 256);
        ARSADDITIONS_SKIP_STOWED_CHARM_TICK = builder
                .comment("Skip inventory ticks for charms that are not equipped.")
                .define("skipStowedCharmTick", true);
        builder.pop();
        builder.comment("Ars Nouveau patches.").push("arsnouveau");
        ARSNOUVEAU_SKY_TEXTURE_INTERVAL = builder
                .comment("Minimum client ticks between sky texture updates.")
                .defineInRange("skyTextureInterval", 1, 0, 200);
        builder.pop();
        builder.comment("Ars N Spells patches.").push("arsnspells");
        ARSNSPELLS_CARRY_COOLDOWNS_THROUGH_CLONE = builder
                .comment("Copy spell cooldown data when a player is cloned.")
                .define("carryCooldownsThroughClone", true);
        ARSNSPELLS_LEAN_MANA_BAR_OVERLAY_GATE = builder
                .comment("Skip the mana overlay when it cannot render.")
                .define("leanManaBarOverlayGate", true);
        ARSNSPELLS_LEAN_RESONANCE_TICK = builder
                .comment("Reduce redundant resonance tick work.")
                .define("leanResonanceTick", true);
        ARSNSPELLS_LEAN_SOURCE_JAR_TICK = builder
                .comment("Reduce redundant source jar synergy work.")
                .define("leanSourceJarTick", true);
        builder.pop();
        builder.comment("Better Combat patches.").push("bettercombat");
        BETTERCOMBAT_CACHE_WEAPON_ATTRIBUTES = builder
                .comment("Cache weapon attributes by item.")
                .define("cacheWeaponAttributes", true);
        BETTERCOMBAT_OFFHAND_ONLY_TWO_HANDED_CHECK = builder
                .comment("Avoid the two-handed check for irrelevant offhand items.")
                .define("offhandOnlyTwoHandedCheck", true);
        builder.pop();
        builder.comment("Biolith patches.").push("biolith");
        BIOLITH_EARLY_REGISTRY_CAPTURE = builder
                .comment("Capture Biolith registries during world stem creation.")
                .define("earlyRegistryCapture", true);
        BIOLITH_RESTAMP_SWAPPED_BIOME_SOURCE = builder
                .comment("Restore swapped biome source dimension stamps.")
                .define("restampSwappedBiomeSource", true);
        BIOLITH_REUSE_BIOME_ENTRIES = builder
                .comment("Reuse cached Biolith biome parameter entries.")
                .define("reuseBiomeEntries", true);
        builder.pop();
        builder.comment("Biomes We've Gone patches.").push("biomeswevegone");
        BIOMESWEVEGONE_SKIP_FOREIGN_CHUNK_TERRAIN = builder
                .comment("Skip terrain extensions for foreign biome chunks.")
                .define("skipForeignChunkTerrain", true);
        builder.pop();
        builder.comment("Born in Chaos patches.").push("borninchaos");
        BORNINCHAOS_SKIP_ITEM_ANIMATION_COPIES = builder
                .comment("Skip redundant item animation copies.")
                .define("skipItemAnimationCopies", true);
        BORNINCHAOS_SKIP_FOREIGN_ENTITY_ANIMATIONS = builder
                .comment("Skip animation work for foreign entities.")
                .define("skipForeignEntityAnimations", true);
        BORNINCHAOS_SKIP_REDUNDANT_DIMENSION_REFRESH = builder
                .comment("Skip redundant entity dimension refreshes.")
                .define("skipRedundantDimensionRefresh", true);
        BORNINCHAOS_NARROW_MINION_SCANS = builder
                .comment("Narrow minion entity scans.")
                .define("narrowMinionScans", true);
        builder.pop();
        builder.comment("Cable Facades patches.").push("cablefacades");
        CABLEFACADES_SKIP_EMPTY_LOOKUP = builder
                .comment("Skip cable facade lookups when no facade is present.")
                .define("skipEmptyLookup", true);
        builder.pop();
        builder.comment("Ars Elemental patches.").push("arselemental");
        ARSELEMENTAL_LEAN_CURIO_FALLBACK = builder
                .comment("Use the Curios inventory cache for failed fallback lookups.")
                .define("leanCurioFallback", true);
        builder.pop();
        builder.comment("Cataclysm patches.").push("cataclysm");
        CATACLYSM_CACHE_SHAKE_SCAN = builder
                .comment("Cache repeated Cataclysm camera shake scans.")
                .define("cacheShakeScan", true);
        builder.pop();
        builder.comment("Chimes patches.").push("chimes");
        CHIMES_SKIP_CLIENT_PHANTOM_SCAN = builder
                .comment("Skip phantom scans on the client.")
                .define("skipClientPhantomScan", true);
        CHIMES_PHANTOM_SCAN_INTERVAL = builder
                .comment("Server ticks between phantom scans.")
                .defineInRange("phantomScanInterval", 4, 1, 40);
        builder.pop();
        builder.comment("Copycats+ patches.").push("copycats");
        COPYCATS_CACHED_MODEL_CONFIG = builder
                .comment("Cache Copycats model configuration lookups.")
                .define("cachedModelConfig", true);
        COPYCATS_FAST_MIGRATION_CHECKS = builder
                .comment("Short circuit Copycats migration checks.")
                .define("fastMigrationChecks", true);
        COPYCATS_LEAN_VIRTUAL_WORLD_CHECK = builder
                .comment("Reduce virtual world checks.")
                .define("leanVirtualWorldCheck", true);
        COPYCATS_MEMO_STATE_OCCLUSION = builder
                .comment("Memoize block state occlusion checks.")
                .define("memoStateOcclusion", true);
        builder.pop();
        builder.comment("Corpse patches.").push("corpse");
        CORPSE_DEATH_SWEEP_INTERVAL = builder
                .comment("Seconds between corpse death sweeps.")
                .defineInRange("deathSweepInterval", 300, 0, 7200);
        CORPSE_IDLE_SCAN_INTERVAL = builder
                .comment("Ticks between idle corpse scans.")
                .defineInRange("idleScanInterval", 20, 0, 200);
        builder.pop();
        builder.comment("Create patches.").push("create");
        CREATE_DEDUPE_BIG_OUTLINE_PROBES = builder
                .comment("Deduplicate Create outline probes.")
                .define("dedupeBigOutlineProbes", true);
        CREATE_BIG_OUTLINE_PICK_INTERVAL = builder
                .comment("Ticks between Create outline picks.")
                .defineInRange("bigOutlinePickInterval", 5, 0, 200);
        builder.pop();
        builder.comment("Create Crafts & Additions patches.").push("createaddition");
        CREATEADDITION_FIX_AMBIENT_SOUND_TOGGLE = builder
                .comment("Avoid redundant ambient sound toggle writes.")
                .define("fixAmbientSoundToggle", true);
        CREATEADDITION_LEAN_HELD_WIRE_CHECK = builder
                .comment("Skip held wire checks when no wire is selected.")
                .define("leanHeldWireCheck", true);
        CREATEADDITION_SOUNDSCAPE_TICK_ONCE = builder
                .comment("Run soundscape ticking once per client tick.")
                .define("soundscapeTickOnce", true);
        builder.pop();
        builder.comment("Cucumber patches.").push("cucumber");
        CUCUMBER_CACHE_TAG_LOOKUP = builder
                .comment("Cache Cucumber tag lookups.")
                .define("cacheTagLookup", true);
        CUCUMBER_LEAN_TAG_TOOLTIP = builder
                .comment("Skip unnecessary Cucumber tag tooltip work.")
                .define("leanTagTooltip", true);
        CUCUMBER_LEAN_TILE_DISPATCH = builder
                .comment("Skip unnecessary Cucumber tile dispatch.")
                .define("leanTileDispatch", true);
        builder.pop();
        builder.comment("Dungeon Crawl patches.").push("dungeoncrawl");
        DUNGEONCRAWL_SKIP_BLOCK_ENTITY_PROBE = builder
                .comment("Skip empty block entity probes.")
                .define("skipBlockEntityProbe", true);
        builder.pop();
        builder.comment("Dungeons Delight patches.").push("dungeonsdelight");
        DUNGEONSDELIGHT_LEAN_MONSTER_EFFECT_SWAP = builder
                .comment("Reduce monster effect swap work.")
                .define("leanMonsterEffectSwap", true);
        DUNGEONSDELIGHT_LEAN_YAM_AURA = builder
                .comment("Reduce Yam aura scans.")
                .define("leanYamAura", true);
        DUNGEONSDELIGHT_NO_DUPLICATE_ZOMBIE_ADD = builder
                .comment("Avoid duplicate zombie additions.")
                .define("noDuplicateZombieAdd", true);
        DUNGEONSDELIGHT_PER_PLAYER_TENACITY_INTERVAL = builder
                .comment("Use the per-player Tenacity interval.")
                .define("perPlayerTenacityInterval", true);
        DUNGEONSDELIGHT_SHARED_FERAL_BITE_RANDOM = builder
                .comment("Reuse Feral Bite random state.")
                .define("sharedFeralBiteRandom", true);
        DUNGEONSDELIGHT_SKIP_INVISIBLE_RUSH_OVERLAY = builder
                .comment("Skip invisible Ravenous Rush overlays.")
                .define("skipInvisibleRushOverlay", true);
        builder.pop();
        builder.comment("Easy Villagers patches.").push("easyvillagers");
        EASYVILLAGERS_DIRTY_MARK_INTERVAL = builder
                .comment("Ticks between dirty mark checks.")
                .defineInRange("dirtyMarkInterval", 20, 0, 200);
        builder.pop();
        builder.comment("Entity Model Features patches.").push("emf");
        EMF_CACHE_BLOCK_ENTITY_TYPE_NAME = builder
                .comment("Cache block entity type names.")
                .define("cacheBlockEntityTypeName", true);
        EMF_CACHE_NBT_PER_TICK = builder
                .comment("Cache NBT animation queries per tick.")
                .define("cacheNbtPerTick", true);
        EMF_DROP_ZERO_ANGER_ENTRIES = builder
                .comment("Drop zero anger animation entries.")
                .define("dropZeroAngerEntries", true);
        builder.pop();
        builder.comment("FancyMenu patches.").push("fancymenu");
        FANCYMENU_CACHE_SCREEN_BLACKLIST = builder
                .comment("Cache FancyMenu screen blacklist checks.")
                .define("cacheScreenBlacklist", true);
        FANCYMENU_CACHE_SCREEN_IDENTIFIERS = builder
                .comment("Cache FancyMenu screen identifiers.")
                .define("cacheScreenIdentifiers", true);
        FANCYMENU_INTERNET_PROBE_SECONDS = builder
                .comment("Seconds between internet availability probes.")
                .defineInRange("internetProbeSeconds", 600, 0, 86400);
        FANCYMENU_PIN_RENDER_STATE_TO_RENDER_THREAD = builder
                .comment("Keep render state on the render thread.")
                .define("pinRenderStateToRenderThread", true);
        FANCYMENU_SEAMLESS_CAPTURE_INTERVAL = builder
                .comment("Milliseconds between seamless captures.")
                .defineInRange("seamlessCaptureInterval", 30, 0, 1600);
        FANCYMENU_SKIP_REDUNDANT_SCALE_WRITES = builder
                .comment("Skip redundant scale writes.")
                .define("skipRedundantScaleWrites", true);
        FANCYMENU_TRACK_POSE_STACK_ONLY_IN_SCREENS = builder
                .comment("Track PoseStack changes only while a screen is open.")
                .define("trackPoseStackOnlyInScreens", true);
        builder.pop();
        builder.comment("Farmer's Delight patches.").push("farmersdelight");
        FARMERSDELIGHT_CACHE_OVERLAY_LOOKUP = builder
                .comment("Cache overlay lookups.")
                .define("cacheOverlayLookup", true);
        FARMERSDELIGHT_LEAN_BASKET_SCAN = builder
                .comment("Reduce basket scans.")
                .define("leanBasketScan", true);
        FARMERSDELIGHT_LEAN_TEMPT_GOAL_PRIORITY = builder
                .comment("Reduce tempt goal priority checks.")
                .define("leanTemptGoalPriority", true);
        FARMERSDELIGHT_SKIP_CLIENT_TEMPT_GOALS = builder
                .comment("Skip client-only tempt goal work.")
                .define("skipClientTemptGoals", true);
        FARMERSDELIGHT_SKIP_EMPTY_STOVE_WORK = builder
                .comment("Skip empty stove work.")
                .define("skipEmptyStoveWork", true);
        FARMERSDELIGHT_SKIP_NOOP_ITEM_SYNC = builder
                .comment("Skip no-op item synchronization.")
                .define("skipNoopItemSync", true);
        builder.pop();

        SPEC = builder.build();
    }

    public static void onLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == SPEC) {
            bake();
        }
    }

    public static void onReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == SPEC) {
            bake();
        }
    }

    private static void bake() {
        if (!SPEC.isLoaded()) {
            return;
        }

        masterEnabled = MASTER_ENABLED.get();
        architecturyDirectEventDispatch = masterEnabled && ARCHITECTURY_DIRECT_EVENT_DISPATCH.get();
        artifactsSkipClientTickOnNonPlayers = masterEnabled && ARTIFACTS_SKIP_CLIENT_TICK_ON_NON_PLAYERS.get();
        xaeroMinimapRenderFpsCap = masterEnabled ? XAERO_MINIMAP_RENDER_FPS_CAP.get() : 0;
        xaeroworldmapVramPollInterval = masterEnabled ? XAERO_WORLD_MAP_VRAM_POLL_INTERVAL.get() : 0;
        xaeroworldmapRenderProcessInterval = masterEnabled ? XAERO_WORLD_MAP_RENDER_PROCESS_INTERVAL.get() : 0;
        xaeroworldmapIdleMapFrameWait = masterEnabled && XAERO_WORLD_MAP_IDLE_MAP_FRAME_WAIT.get();
        xaeroworldmapMapFrameSpinTail = masterEnabled ? XAERO_WORLD_MAP_MAP_FRAME_SPIN_TAIL.get() : 0;
        xaeroworldmapUploadHeadroomMicros = masterEnabled ? XAERO_WORLD_MAP_UPLOAD_HEADROOM_MICROS.get() : 3000;
        xaeroworldmapOrphanUploadPbo = masterEnabled && XAERO_WORLD_MAP_ORPHAN_UPLOAD_PBO.get();
        xaeroworldmapOpenReflectedFields = masterEnabled && XAERO_WORLD_MAP_OPEN_REFLECTED_FIELDS.get();
        lootrSkipIdleBlockEntityTicker = masterEnabled && LOOTR_SKIP_IDLE_BLOCK_ENTITY_TICKER.get();
        lootrBlockEntityTickerBudget = masterEnabled ? LOOTR_BLOCK_ENTITY_TICKER_BUDGET.get() : 0;
        patchouliCacheBookItemLookup = masterEnabled && PATCHOULI_CACHE_BOOK_ITEM_LOOKUP.get();
        ftbchunksSkipHiddenMinimapWork = masterEnabled && FTBCHUNKS_SKIP_HIDDEN_MINIMAP_WORK.get();
        ftbchunksFastRegionWrite = masterEnabled && FTBCHUNKS_FAST_REGION_WRITE.get();
        ftbchunksDisableMapWriting = masterEnabled && FTBCHUNKS_DISABLE_MAP_WRITING.get();
        curiosSkipSlotlessEntities = masterEnabled && CURIOS_SKIP_SLOTLESS_ENTITIES.get();
        curiosSkipClientTickOnNonPlayers = masterEnabled && CURIOS_SKIP_CLIENT_TICK_ON_NON_PLAYERS.get();
        curiosCacheEntitySlotLookup = masterEnabled && CURIOS_CACHE_ENTITY_SLOT_LOOKUP.get();
        curiosFastEquippedItemMiss = masterEnabled && CURIOS_FAST_EQUIPPED_ITEM_MISS.get();
        curiosFastFindFirstMiss = masterEnabled && CURIOS_FAST_FIND_FIRST_MISS.get();
        curiosReuseMapView = masterEnabled && CURIOS_REUSE_MAP_VIEW.get();
        curiosReconcileCachedSlotModifiers = masterEnabled && CURIOS_RECONCILE_CACHED_SLOT_MODIFIERS.get();
        curiosThreadSafeCaches = masterEnabled && CURIOS_THREAD_SAFE_CACHES.get();
        terrablenderCacheNamespaceRule = masterEnabled && TERRABLENDER_CACHE_NAMESPACE_RULE.get();
        terrablenderThreadLocalAreaCache = masterEnabled && TERRABLENDER_THREAD_LOCAL_AREA_CACHE.get();
        zetaLeanStructureReplacement = masterEnabled && ZETA_LEAN_STRUCTURE_REPLACEMENT.get();
        zetaShareEventWrappers = masterEnabled && ZETA_SHARE_EVENT_WRAPPERS.get();
        ambientsoundsMemoBiomeMatch = masterEnabled && AMBIENTSOUNDS_MEMO_BIOME_MATCH.get();
        balmMemoDynamicModelKeys = masterEnabled && BALM_MEMO_DYNAMIC_MODEL_KEYS.get();
        balmCacheConfigIdentifier = masterEnabled && BALM_CACHE_CONFIG_IDENTIFIER.get();
        caelusSkipGroundedNonPlayers = masterEnabled && CAELUS_SKIP_GROUNDED_NON_PLAYERS.get();
        adastraMemoPlanetDefaults = masterEnabled && ADASTRA_MEMO_PLANET_DEFAULTS.get();
        armageddonCacheProgressionIds = masterEnabled && ARMAGEDDON_CACHE_PROGRESSION_IDS.get();
        armageddonSkipForeignEntityAnimations = masterEnabled && ARMAGEDDON_SKIP_FOREIGN_ENTITY_ANIMATIONS.get();
        arsadditionsCacheRuinedPortalScan = masterEnabled && ARSADDITIONS_CACHE_RUINED_PORTAL_SCAN.get();
        arsadditionsMagelightParticleDistance = masterEnabled ? ARSADDITIONS_MAGELIGHT_PARTICLE_DISTANCE.get() : 0;
        arsadditionsSkipStowedCharmTick = masterEnabled && ARSADDITIONS_SKIP_STOWED_CHARM_TICK.get();
        arsnouveauSkyTextureInterval = masterEnabled ? ARSNOUVEAU_SKY_TEXTURE_INTERVAL.get() : 0;
        arsnspellsCarryCooldownsThroughClone = masterEnabled && ARSNSPELLS_CARRY_COOLDOWNS_THROUGH_CLONE.get();
        arsnspellsLeanManaBarOverlayGate = masterEnabled && ARSNSPELLS_LEAN_MANA_BAR_OVERLAY_GATE.get();
        arsnspellsLeanResonanceTick = masterEnabled && ARSNSPELLS_LEAN_RESONANCE_TICK.get();
        arsnspellsLeanSourceJarTick = masterEnabled && ARSNSPELLS_LEAN_SOURCE_JAR_TICK.get();
        bettercombatCacheWeaponAttributes = masterEnabled && BETTERCOMBAT_CACHE_WEAPON_ATTRIBUTES.get();
        bettercombatOffhandOnlyTwoHandedCheck = masterEnabled && BETTERCOMBAT_OFFHAND_ONLY_TWO_HANDED_CHECK.get();
        biolithEarlyRegistryCapture = masterEnabled && BIOLITH_EARLY_REGISTRY_CAPTURE.get();
        biolithRestampSwappedBiomeSource = masterEnabled && BIOLITH_RESTAMP_SWAPPED_BIOME_SOURCE.get();
        biolithReuseBiomeEntries = masterEnabled && BIOLITH_REUSE_BIOME_ENTRIES.get();
        copycatsCachedModelConfig = masterEnabled && COPYCATS_CACHED_MODEL_CONFIG.get();
        copycatsFastMigrationChecks = masterEnabled && COPYCATS_FAST_MIGRATION_CHECKS.get();
        copycatsLeanVirtualWorldCheck = masterEnabled && COPYCATS_LEAN_VIRTUAL_WORLD_CHECK.get();
        copycatsMemoStateOcclusion = masterEnabled && COPYCATS_MEMO_STATE_OCCLUSION.get();
        corpseDeathSweepInterval = masterEnabled ? CORPSE_DEATH_SWEEP_INTERVAL.get() : 0;
        corpseIdleScanInterval = masterEnabled ? CORPSE_IDLE_SCAN_INTERVAL.get() : 0;
        createadditionFixAmbientSoundToggle = masterEnabled && CREATEADDITION_FIX_AMBIENT_SOUND_TOGGLE.get();
        createadditionLeanHeldWireCheck = masterEnabled && CREATEADDITION_LEAN_HELD_WIRE_CHECK.get();
        createadditionSoundscapeTickOnce = masterEnabled && CREATEADDITION_SOUNDSCAPE_TICK_ONCE.get();
        createDedupeBigOutlineProbes = masterEnabled && CREATE_DEDUPE_BIG_OUTLINE_PROBES.get();
        createBigOutlinePickInterval = masterEnabled ? CREATE_BIG_OUTLINE_PICK_INTERVAL.get() : 0;
        cucumberCacheTagLookup = masterEnabled && CUCUMBER_CACHE_TAG_LOOKUP.get();
        cucumberLeanTagTooltip = masterEnabled && CUCUMBER_LEAN_TAG_TOOLTIP.get();
        cucumberLeanTileDispatch = masterEnabled && CUCUMBER_LEAN_TILE_DISPATCH.get();
        dungeoncrawlSkipBlockEntityProbe = masterEnabled && DUNGEONCRAWL_SKIP_BLOCK_ENTITY_PROBE.get();
        dungeonsdelightLeanMonsterEffectSwap = masterEnabled && DUNGEONSDELIGHT_LEAN_MONSTER_EFFECT_SWAP.get();
        dungeonsdelightLeanYamAura = masterEnabled && DUNGEONSDELIGHT_LEAN_YAM_AURA.get();
        dungeonsdelightNoDuplicateZombieAdd = masterEnabled && DUNGEONSDELIGHT_NO_DUPLICATE_ZOMBIE_ADD.get();
        dungeonsdelightPerPlayerTenacityInterval = masterEnabled && DUNGEONSDELIGHT_PER_PLAYER_TENACITY_INTERVAL.get();
        dungeonsdelightSharedFeralBiteRandom = masterEnabled && DUNGEONSDELIGHT_SHARED_FERAL_BITE_RANDOM.get();
        dungeonsdelightSkipInvisibleRushOverlay = masterEnabled && DUNGEONSDELIGHT_SKIP_INVISIBLE_RUSH_OVERLAY.get();
        easyvillagersDirtyMarkInterval = masterEnabled ? EASYVILLAGERS_DIRTY_MARK_INTERVAL.get() : 0;
        emfCacheBlockEntityTypeName = masterEnabled && EMF_CACHE_BLOCK_ENTITY_TYPE_NAME.get();
        emfCacheNbtPerTick = masterEnabled && EMF_CACHE_NBT_PER_TICK.get();
        emfDropZeroAngerEntries = masterEnabled && EMF_DROP_ZERO_ANGER_ENTRIES.get();
        fancymenuCacheScreenBlacklist = masterEnabled && FANCYMENU_CACHE_SCREEN_BLACKLIST.get();
        fancymenuCacheScreenIdentifiers = masterEnabled && FANCYMENU_CACHE_SCREEN_IDENTIFIERS.get();
        fancymenuInternetProbeSeconds = masterEnabled ? FANCYMENU_INTERNET_PROBE_SECONDS.get() : 0;
        fancymenuPinRenderStateToRenderThread = masterEnabled && FANCYMENU_PIN_RENDER_STATE_TO_RENDER_THREAD.get();
        fancymenuSeamlessCaptureInterval = masterEnabled ? FANCYMENU_SEAMLESS_CAPTURE_INTERVAL.get() : 0;
        fancymenuSkipRedundantScaleWrites = masterEnabled && FANCYMENU_SKIP_REDUNDANT_SCALE_WRITES.get();
        fancymenuTrackPoseStackOnlyInScreens = masterEnabled && FANCYMENU_TRACK_POSE_STACK_ONLY_IN_SCREENS.get();
        farmersdelightCacheOverlayLookup = masterEnabled && FARMERSDELIGHT_CACHE_OVERLAY_LOOKUP.get();
        farmersdelightLeanBasketScan = masterEnabled && FARMERSDELIGHT_LEAN_BASKET_SCAN.get();
        farmersdelightLeanTemptGoalPriority = masterEnabled && FARMERSDELIGHT_LEAN_TEMPT_GOAL_PRIORITY.get();
        farmersdelightSkipClientTemptGoals = masterEnabled && FARMERSDELIGHT_SKIP_CLIENT_TEMPT_GOALS.get();
        farmersdelightSkipEmptyStoveWork = masterEnabled && FARMERSDELIGHT_SKIP_EMPTY_STOVE_WORK.get();
        farmersdelightSkipNoopItemSync = masterEnabled && FARMERSDELIGHT_SKIP_NOOP_ITEM_SYNC.get();
        chimesSkipClientPhantomScan = masterEnabled && CHIMES_SKIP_CLIENT_PHANTOM_SCAN.get();
        chimesPhantomScanInterval = masterEnabled ? CHIMES_PHANTOM_SCAN_INTERVAL.get() : 1;
        cataclysmCacheShakeScan = masterEnabled && CATACLYSM_CACHE_SHAKE_SCAN.get();
        biomeswevegoneSkipForeignChunkTerrain = masterEnabled && BIOMESWEVEGONE_SKIP_FOREIGN_CHUNK_TERRAIN.get();
        borninchaosSkipItemAnimationCopies = masterEnabled && BORNINCHAOS_SKIP_ITEM_ANIMATION_COPIES.get();
        borninchaosSkipForeignEntityAnimations = masterEnabled && BORNINCHAOS_SKIP_FOREIGN_ENTITY_ANIMATIONS.get();
        borninchaosSkipRedundantDimensionRefresh = masterEnabled && BORNINCHAOS_SKIP_REDUNDANT_DIMENSION_REFRESH.get();
        borninchaosNarrowMinionScans = masterEnabled && BORNINCHAOS_NARROW_MINION_SCANS.get();
        cablefacadesSkipEmptyLookup = masterEnabled && CABLEFACADES_SKIP_EMPTY_LOOKUP.get();
        arselementalLeanCurioFallback = masterEnabled && ARSELEMENTAL_LEAN_CURIO_FALLBACK.get();
    }

    private CoOConfig() {
    }
}
