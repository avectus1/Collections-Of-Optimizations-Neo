package com.misanthropy.collections_of_optimizations;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.event.config.ModConfigEvent;

public final class CoOConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.BooleanValue MASTER_ENABLED;
    public static final ModConfigSpec.IntValue XAERO_MINIMAP_RENDER_FPS_CAP;
    public static final ModConfigSpec.BooleanValue LOOTR_SKIP_IDLE_BLOCK_ENTITY_TICKER;
    public static final ModConfigSpec.IntValue LOOTR_BLOCK_ENTITY_TICKER_BUDGET;
    public static final ModConfigSpec.BooleanValue PATCHOULI_CACHE_BOOK_ITEM_LOOKUP;
    public static final ModConfigSpec.BooleanValue FTBCHUNKS_SKIP_HIDDEN_MINIMAP_WORK;
    public static final ModConfigSpec.BooleanValue FTBCHUNKS_FAST_REGION_WRITE;
    public static final ModConfigSpec.BooleanValue CURIOS_SKIP_SLOTLESS_ENTITIES;
    public static final ModConfigSpec.BooleanValue CURIOS_SKIP_CLIENT_TICK_ON_NON_PLAYERS;
    public static final ModConfigSpec.BooleanValue CURIOS_CACHE_ENTITY_SLOT_LOOKUP;
    public static final ModConfigSpec.BooleanValue CURIOS_FAST_EQUIPPED_ITEM_MISS;
    public static final ModConfigSpec.BooleanValue TERRABLENDER_CACHE_NAMESPACE_RULE;
    public static final ModConfigSpec.BooleanValue ZETA_LEAN_STRUCTURE_REPLACEMENT;
    public static final ModConfigSpec.BooleanValue ZETA_SHARE_EVENT_WRAPPERS;

    public static volatile boolean masterEnabled;
    public static volatile int xaeroMinimapRenderFpsCap;
    public static volatile boolean lootrSkipIdleBlockEntityTicker;
    public static volatile int lootrBlockEntityTickerBudget;
    public static volatile boolean patchouliCacheBookItemLookup;
    public static volatile boolean ftbchunksSkipHiddenMinimapWork;
    public static volatile boolean ftbchunksFastRegionWrite;
    public static volatile boolean curiosSkipSlotlessEntities;
    public static volatile boolean curiosSkipClientTickOnNonPlayers;
    public static volatile boolean curiosCacheEntitySlotLookup;
    public static volatile boolean curiosFastEquippedItemMiss;
    public static volatile boolean terrablenderCacheNamespaceRule;
    public static volatile boolean zetaLeanStructureReplacement;
    public static volatile boolean zetaShareEventWrappers;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        MASTER_ENABLED = builder
                .comment("Enable all Collections of Optimizations patches.")
                .define("masterEnabled", true);
        builder.comment("Xaero's Minimap patches.").push("xaerominimap");
        XAERO_MINIMAP_RENDER_FPS_CAP = builder
                .comment("Maximum number of Minimap FBO renders per second. Set to 0 to disable the cap.")
                .defineInRange("renderFpsCap", 30, 0, 240);
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
        builder.pop();

        builder.comment("TerraBlender patches.").push("terrablender");
        TERRABLENDER_CACHE_NAMESPACE_RULE = builder
                .comment("Cache TerraBlender's namespace rule lookup for the current biome.")
                .define("cacheNamespaceRule", true);
        builder.pop();

        builder.comment("Zeta patches.").push("zeta");
        ZETA_LEAN_STRUCTURE_REPLACEMENT = builder
                .comment("Reuse a snapshot of Zeta structure replacement functions.")
                .define("leanStructureReplacement", true);
        ZETA_SHARE_EVENT_WRAPPERS = builder
                .comment("Reuse a Zeta event wrapper while dispatching the same Forge event.")
                .define("shareEventWrappers", true);
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
        xaeroMinimapRenderFpsCap = masterEnabled ? XAERO_MINIMAP_RENDER_FPS_CAP.get() : 0;
        lootrSkipIdleBlockEntityTicker = masterEnabled && LOOTR_SKIP_IDLE_BLOCK_ENTITY_TICKER.get();
        lootrBlockEntityTickerBudget = masterEnabled ? LOOTR_BLOCK_ENTITY_TICKER_BUDGET.get() : 0;
        patchouliCacheBookItemLookup = masterEnabled && PATCHOULI_CACHE_BOOK_ITEM_LOOKUP.get();
        ftbchunksSkipHiddenMinimapWork = masterEnabled && FTBCHUNKS_SKIP_HIDDEN_MINIMAP_WORK.get();
        ftbchunksFastRegionWrite = masterEnabled && FTBCHUNKS_FAST_REGION_WRITE.get();
        curiosSkipSlotlessEntities = masterEnabled && CURIOS_SKIP_SLOTLESS_ENTITIES.get();
        curiosSkipClientTickOnNonPlayers = masterEnabled && CURIOS_SKIP_CLIENT_TICK_ON_NON_PLAYERS.get();
        curiosCacheEntitySlotLookup = masterEnabled && CURIOS_CACHE_ENTITY_SLOT_LOOKUP.get();
        curiosFastEquippedItemMiss = masterEnabled && CURIOS_FAST_EQUIPPED_ITEM_MISS.get();
        terrablenderCacheNamespaceRule = masterEnabled && TERRABLENDER_CACHE_NAMESPACE_RULE.get();
        zetaLeanStructureReplacement = masterEnabled && ZETA_LEAN_STRUCTURE_REPLACEMENT.get();
        zetaShareEventWrappers = masterEnabled && ZETA_SHARE_EVENT_WRAPPERS.get();
    }

    private CoOConfig() {
    }
}
