package com.misanthropy.collections_of_optimizations.mixin.obscuretooltips;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.core.TooltipIconGuard;
import dev.obscuria.tooltips.client.TooltipRenderer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

/** Marks the time Obscure Tooltips spends drawing a tooltip (see MixinLodestoneTooltipIconParticles). */
@Mixin(value = TooltipRenderer.class, remap = false)
public abstract class MixinObscureTooltipRenderGuard {
    @WrapMethod(method = "render")
    private static boolean coo$guard(GuiGraphics graphics, Font font, List<ClientTooltipComponent> components, int mouseX, int mouseY,
                                     ClientTooltipPositioner positioner, Operation<Boolean> original) {
        TooltipIconGuard.depth++;
        try {
            return original.call(graphics, font, components, mouseX, mouseY, positioner);
        } finally {
            TooltipIconGuard.depth--;
        }
    }
}
