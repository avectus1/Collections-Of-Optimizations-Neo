package com.misanthropy.collections_of_optimizations.mixin.placebo;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import dev.shadowsoffire.placebo.util.CachedObject;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.ToIntFunction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * The hasher runs on every CachedObject.get (Apotheosis affix lookups and similar). The original builds a
 * stream, a method reference and an array per call; this loop gives the exact same Arrays.hashCode value.
 */
@Mixin(value = CachedObject.class, remap = false)
public abstract class MixinPlaceboHashComponents {
    /**
     * @author collections_of_optimizations
     * @reason same hash, no stream or array per call
     */
    @Overwrite
    public static ToIntFunction<ItemStack> hashComponents(DataComponentType<?>... types) {
        final DataComponentType<?>[] copy = types.clone();
        if (!CoOConfig.placeboHashComponents) {
            return stack -> Arrays.hashCode(Arrays.stream(copy).<Object>map(stack::get).filter(Objects::nonNull).toArray());
        }
        return stack -> {
            int hash = 1;
            for (DataComponentType<?> type : copy) {
                Object value = stack.get(type);
                if (value != null) {
                    hash = 31 * hash + value.hashCode();
                }
            }
            return hash;
        };
    }
}
