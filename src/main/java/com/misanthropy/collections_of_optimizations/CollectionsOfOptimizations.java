package com.misanthropy.collections_of_optimizations;

import com.misanthropy.collections_of_optimizations.core.CurioPresenceCache;
import com.misanthropy.collections_of_optimizations.core.CuriosSlotCache;
import com.misanthropy.collections_of_optimizations.core.ClientTickStamp;
import com.misanthropy.collections_of_optimizations.core.CameraShakeScanCache;
import com.misanthropy.collections_of_optimizations.core.ClientLevelRelease;
import com.misanthropy.collections_of_optimizations.core.ElysiumSoundTransformers;
import com.misanthropy.collections_of_optimizations.core.EmiHistoryRelease;
import com.misanthropy.collections_of_optimizations.core.GhostPlayerPurge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(CollectionsOfOptimizations.MOD_ID)
public final class CollectionsOfOptimizations {
    public static final String MOD_ID = "collections_of_optimizations";

    public CollectionsOfOptimizations(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, CoOConfig.SPEC);
        modEventBus.addListener(CoOConfig::onLoad);
        modEventBus.addListener(CoOConfig::onReload);
        if (Boolean.getBoolean("coo.audit")) {
            modEventBus.addListener(net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent.class, event -> {
                org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger("coo-audit");
                try {
                    org.spongepowered.asm.mixin.MixinEnvironment.getCurrentEnvironment().audit();
                    log.info("[coo-audit] mixin audit done");
                } catch (Throwable throwable) {
                    log.error("[coo-audit] audit threw", throwable);
                }
            });
        }
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientConfigScreen.register(modContainer);
            ClientTickStamp.register();
            CameraShakeScanCache.register();
            ClientLevelRelease.register();
            if (ModList.get().getModContainerById("flywheel").filter(mod -> mod.getModInfo().getVersion().getMajorVersion() >= 1).isPresent()) {
                com.misanthropy.collections_of_optimizations.core.FlywheelWarm.register();
            }
            if (ModList.get().isLoaded("xaerolib")) {
                com.misanthropy.collections_of_optimizations.core.XaeroConfigFrame.register();
            }
            com.misanthropy.collections_of_optimizations.core.ClientUnloadRelease.register();
            if (ModList.get().isLoaded("citadel") || ModList.get().isLoaded("alexscaves") || ModList.get().isLoaded("xaerominimap")) {
                com.misanthropy.collections_of_optimizations.core.ModClientLeakGuards.register();
            }
            if (ModList.get().isLoaded("dungeonnowloading")) {
                com.misanthropy.collections_of_optimizations.core.DnlClientLeakGuard.register();
            }
            if (ModList.get().isLoaded("emi")) {
                EmiHistoryRelease.register();
            }
        }
        GhostPlayerPurge.register();
        com.misanthropy.collections_of_optimizations.core.StructureTemplateRelease.register();
        com.misanthropy.collections_of_optimizations.core.ModLeakGuards.register();
        if (ModList.get().isLoaded("more_quest_types")) {
            com.misanthropy.collections_of_optimizations.core.MqtBufferSweep.register();
        }
        if (ModList.get().isLoaded("dungeonnowloading")) {
            com.misanthropy.collections_of_optimizations.core.DnlLeakGuard.register();
        }
        if (ModList.get().isLoaded("elysium_api")) {
            ElysiumSoundTransformers.register();
        }
        if (ModList.get().isLoaded("curios")) {
            CuriosSlotCache.register();
            CurioPresenceCache.register();
        }
    }
}
