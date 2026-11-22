package com.misanthropy.collections_of_optimizations.mixin.xaerolib;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.XaeroConfigFrame;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.IdentityHashMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.lib.client.config.ClientConfigManager;
import xaero.lib.common.config.option.ConfigOption;
import xaero.lib.common.config.profile.ConfigProfile;

/**
 * Xaero's minimap and world map ask their config for the effective value of an option thousands of times
 * per frame, and every ask walks the value redirectors, the server enforcement check, the synced server
 * config and the profile table. Within one rendered frame the answer cannot change, so the render thread
 * remembers each answer until the next frame starts.
 */
@Mixin(value = ClientConfigManager.class, remap = false)
public abstract class MixinClientConfigFrameMemo {
    @Unique
    private static final Object COO$NULL = new Object();

    @Unique
    private final IdentityHashMap<ConfigOption<?>, Object> coo$answers = new IdentityHashMap<>();

    @Unique
    private int coo$frame = -1;

    @Unique
    private ConfigProfile coo$profile;

    @Inject(method = "getEffective(Lxaero/lib/common/config/profile/ConfigProfile;Lxaero/lib/common/config/option/ConfigOption;)Ljava/lang/Object;",
            at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$answerFromFrame(ConfigProfile profile, ConfigOption<?> option, CallbackInfoReturnable<Object> cir) {
        if (!CoOConfig.xaerolibFrameConfigMemo || !RenderSystem.isOnRenderThread()) {
            return;
        }
        int frame = XaeroConfigFrame.frame;
        if (frame != this.coo$frame || profile != this.coo$profile) {
            this.coo$answers.clear();
            this.coo$frame = frame;
            this.coo$profile = profile;
            return;
        }
        Object known = this.coo$answers.get(option);
        if (known != null) {
            cir.setReturnValue(known == COO$NULL ? null : known);
        }
    }

    @Inject(method = "getEffective(Lxaero/lib/common/config/profile/ConfigProfile;Lxaero/lib/common/config/option/ConfigOption;)Ljava/lang/Object;",
            at = @At("RETURN"), require = 0)
    private void coo$rememberForFrame(ConfigProfile profile, ConfigOption<?> option, CallbackInfoReturnable<Object> cir) {
        if (CoOConfig.xaerolibFrameConfigMemo && RenderSystem.isOnRenderThread()
                && this.coo$frame == XaeroConfigFrame.frame && profile == this.coo$profile) {
            Object value = cir.getReturnValue();
            this.coo$answers.put(option, value == null ? COO$NULL : value);
        }
    }
}
