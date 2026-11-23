package com.misanthropy.collections_of_optimizations.mixin.alexscaves;

import com.github.alexmodguy.alexscaves.client.render.misc.CaveMapRenderer;
import java.util.Map;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = CaveMapRenderer.class, remap = false)
public interface CaveMapRendererAccessor {
    @Accessor("CAVE_MAPS_HAND")
    static Map<ItemStack, CaveMapRenderer> coo$hand() {
        throw new AssertionError();
    }

    @Accessor("CAVE_MAPS_ITEM_FRAME")
    static Map<ItemStack, CaveMapRenderer> coo$frame() {
        throw new AssertionError();
    }
}
