package com.misanthropy.collections_of_optimizations.mixin.ironsspellbooks;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import io.redspace.ironsspellbooks.item.curios.AffinityRing;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Affinity rings use a "builtin/entity" model, which counts as a 3D (block-lit) item, so GUI item rendering keeps
 * 3D lighting on. The ring renderer switches to flat lighting itself and back to 3D lighting right after, but the
 * ring's quads are usually drawn later, when the GUI flushes, by which point 3D lighting is back. The flat ring
 * comes out at about half brightness in slots and tooltips. Treating the ring as a flat item makes the GUI keep
 * flat lighting on until it has drawn it (see MixinIsbAffinityRingRendererLight).
 */
@Mixin(GuiGraphics.class)
public abstract class MixinIsbAffinityRingGuiLight {
    @WrapOperation(method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/BakedModel;usesBlockLight()Z"))
    private boolean coo$flatAffinityRing(BakedModel model, Operation<Boolean> original, LivingEntity entity, Level level, ItemStack stack,
                                         int x, int y, int seed, int guiOffset) {
        if (CoOConfig.isbAffinityRingGuiLight && stack.getItem() instanceof AffinityRing) {
            return false;
        }
        return original.call(model);
    }
}
