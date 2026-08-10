package com.misanthropy.collections_of_optimizations;

import com.misanthropy.collections_of_optimizations.core.CurioPresenceCache;
import com.misanthropy.collections_of_optimizations.core.CuriosSlotCache;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(CollectionsOfOptimizations.MOD_ID)
public final class CollectionsOfOptimizations {
    public static final String MOD_ID = "collections_of_optimizations";

    public CollectionsOfOptimizations(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, CoOConfig.SPEC);
        if (ModList.get().isLoaded("curios")) {
            CuriosSlotCache.register();
            CurioPresenceCache.register();
        }
    }
}
