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
    }

    private CoOConfig() {
    }
}
