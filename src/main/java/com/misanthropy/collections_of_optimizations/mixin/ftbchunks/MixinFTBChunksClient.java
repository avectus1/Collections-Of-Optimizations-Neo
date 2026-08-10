package com.misanthropy.collections_of_optimizations.mixin.ftbchunks;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import dev.ftb.mods.ftbchunks.FTBChunksWorldConfig;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.DeltaTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FTBChunksClient.class, remap = false)
public abstract class MixinFTBChunksClient {
    @Inject(
            method = "renderHud",
            at = @At(value = "INVOKE", target = "Ldev/ftb/mods/ftbchunks/client/FTBChunksClient;getZoom()F"),
            cancellable = true,
            require = 0
    )
    private void coo$skipHiddenMinimapWork(GuiGraphics graphics, DeltaTracker tickDelta, CallbackInfo ci) {
        if (!CoOConfig.FTBCHUNKS_SKIP_HIDDEN_MINIMAP_WORK.get()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui
                || mc.getDebugOverlay().showDebugScreen()
                || !FTBChunksClientConfig.MINIMAP_ENABLED.get()
                || FTBChunksClientConfig.MINIMAP_VISIBILITY.get() == 0
                || !FTBChunksWorldConfig.shouldShowMinimap(mc.player)) {
            ci.cancel();
        }
    }
}
