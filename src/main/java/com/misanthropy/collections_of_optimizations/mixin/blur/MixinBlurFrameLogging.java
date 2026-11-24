package com.misanthropy.collections_of_optimizations.mixin.blur;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import eu.midnightdust.blur.Blur;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Blur+ logs several debug lines on every frame (the current screen's class name, animation targets...).
 * NeoForge's debug.log records debug messages, so this was formatting and writing log lines every frame
 * for the whole session. Drop just those per-frame lines.
 */
@Mixin(value = Blur.class, remap = false)
public abstract class MixinBlurFrameLogging {
    @Redirect(method = {"onRender", "onRenderEnd"}, at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;debug(Ljava/lang/String;)V"), require = 0)
    private static void coo$quiet(Logger logger, String message) {
        if (!CoOConfig.blurFrameLogging) {
            logger.debug(message);
        }
    }

    @Redirect(method = {"onRender", "onRenderEnd"}, at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;debug(Ljava/lang/String;Ljava/lang/Object;)V"), require = 0)
    private static void coo$quiet(Logger logger, String message, Object arg) {
        if (!CoOConfig.blurFrameLogging) {
            logger.debug(message, arg);
        }
    }

    @Redirect(method = {"onRender", "onRenderEnd"}, at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;debug(Ljava/lang/String;[Ljava/lang/Object;)V"), require = 0)
    private static void coo$quiet(Logger logger, String message, Object[] args) {
        if (!CoOConfig.blurFrameLogging) {
            logger.debug(message, args);
        }
    }
}
