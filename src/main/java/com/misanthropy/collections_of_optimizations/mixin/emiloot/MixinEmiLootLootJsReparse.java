package com.misanthropy.collections_of_optimizations.mixin.emiloot;

import com.google.gson.JsonElement;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import fzzyhmstrs.emi_loot.EMILoot;
import java.util.List;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * EMI Loot reads the loot tables before LootJS has changed them, so its loot pages show the stock drops.
 * Read them again once the loot registry is complete (from EMI Loot + LootJS Fix by momo, MIT). The resource
 * manager and ops are let go right after, instead of being kept until the next reload.
 */
@Mixin(value = ReloadableServerRegistries.class, priority = 900, remap = false)
public abstract class MixinEmiLootLootJsReparse {
    @Unique
    private static volatile ResourceManager coo$resourceManager;
    @Unique
    private static volatile RegistryOps<JsonElement> coo$registryOps;

    @Inject(
            method = "lambda$scheduleElementParse$4(Lnet/minecraft/world/level/storage/loot/LootDataType;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/resources/RegistryOps;)Lnet/minecraft/core/WritableRegistry;",
            at = @At("RETURN"),
            require = 0
    )
    private static void coo$captureLootContext(LootDataType<?> type, ResourceManager resourceManager, RegistryOps<JsonElement> ops,
                                               CallbackInfoReturnable<WritableRegistry<?>> cir) {
        if (type == LootDataType.TABLE && CoOConfig.emiLootReparseAfterLootJs) {
            coo$resourceManager = resourceManager;
            coo$registryOps = ops;
        }
    }

    @Inject(method = "apply", at = @At("RETURN"), require = 0)
    @SuppressWarnings("unchecked")
    private static void coo$reparseAfterLootJs(LayeredRegistryAccess<RegistryLayer> registries, List<WritableRegistry<?>> writable,
                                               CallbackInfoReturnable<LayeredRegistryAccess<RegistryLayer>> cir) {
        ResourceManager resourceManager = coo$resourceManager;
        RegistryOps<JsonElement> ops = coo$registryOps;
        coo$resourceManager = null;
        coo$registryOps = null;
        if (resourceManager == null || ops == null || !CoOConfig.emiLootReparseAfterLootJs) {
            return;
        }
        for (WritableRegistry<?> registry : writable) {
            if (registry.key().equals(Registries.LOOT_TABLE)) {
                EMILoot.parseTables(resourceManager, (Registry<LootTable>) registry, ops);
                return;
            }
        }
    }
}
