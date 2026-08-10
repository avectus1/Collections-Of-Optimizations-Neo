package com.misanthropy.collections_of_optimizations;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class CoOConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.IntValue XAERO_MINIMAP_RENDER_FPS_CAP;
    public static final ModConfigSpec.BooleanValue LOOTR_SKIP_IDLE_BLOCK_ENTITY_TICKER;
    public static final ModConfigSpec.IntValue LOOTR_BLOCK_ENTITY_TICKER_BUDGET;
    public static final ModConfigSpec.BooleanValue PATCHOULI_CACHE_BOOK_ITEM_LOOKUP;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
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

        SPEC = builder.build();
    }

    private CoOConfig() {
    }
}
