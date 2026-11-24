package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.mixin.emiloot.EmiLootParserAccessor;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import java.util.LinkedList;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/**
 * EMI Loot keeps the last world's whole loot table registry, its registry access and every parsed loot table in static
 * fields after the world closes, so in singleplayer the old server's data stays in memory until the next world loads.
 * They are rebuilt when a world loads, so drop them when the server stops.
 */
public final class EmiLootLeakGuard {
    private EmiLootLeakGuard() {
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(ServerStoppedEvent.class, e -> {
            if (!CoOConfig.emiLootServerData) {
                return;
            }
            EmiLootParserAccessor.coo$chest().clear();
            EmiLootParserAccessor.coo$block().clear();
            EmiLootParserAccessor.coo$mob().clear();
            EmiLootParserAccessor.coo$gameplay().clear();
            EmiLootParserAccessor.coo$archaeology().clear();
            LootTableParser.lootManager = null;
            LootTableParser.registryOps = null;
            LootTableParser.parsedDirectDrops = new LinkedList<>();
        });
    }
}
