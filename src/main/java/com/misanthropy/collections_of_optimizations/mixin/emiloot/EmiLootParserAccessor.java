package com.misanthropy.collections_of_optimizations.mixin.emiloot;

import fzzyhmstrs.emi_loot.parser.LootTableParser;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = LootTableParser.class, remap = false)
public interface EmiLootParserAccessor {
    @Accessor("chestSenders")
    static Map<ResourceLocation, ?> coo$chest() {
        throw new AssertionError();
    }

    @Accessor("blockSenders")
    static Map<ResourceLocation, ?> coo$block() {
        throw new AssertionError();
    }

    @Accessor("mobSenders")
    static Map<ResourceLocation, ?> coo$mob() {
        throw new AssertionError();
    }

    @Accessor("gameplaySenders")
    static Map<ResourceLocation, ?> coo$gameplay() {
        throw new AssertionError();
    }

    @Accessor("archaeologySenders")
    static Map<ResourceLocation, ?> coo$archaeology() {
        throw new AssertionError();
    }
}
