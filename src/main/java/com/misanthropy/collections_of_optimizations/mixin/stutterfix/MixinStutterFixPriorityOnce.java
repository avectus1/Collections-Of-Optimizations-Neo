package com.misanthropy.collections_of_optimizations.mixin.stutterfix;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * StutterFix applies its server thread priority from inside the server loop, so it asks the OS to set
 * the same priority on every tick. Only make the system call when the priority would actually change.
 */
@Mixin(targets = "net.wisecase2.stutterfix.StutterFix", remap = false)
public abstract class MixinStutterFixPriorityOnce {
    @Unique
    private static Thread coo$lastThread;
    @Unique
    private static int coo$lastRequested;
    @Unique
    private static int coo$lastResult;

    @Redirect(method = {"configPriorityServerThread", "configPriorityRenderThread"},
            at = @At(value = "INVOKE", target = "Ljava/lang/Thread;setPriority(I)V"))
    private static void coo$setOnce(Thread thread, int priority) {
        if (CoOConfig.stutterfixPriorityOnce) {
            int now = thread.getPriority();
            if (now == priority || (thread == coo$lastThread && priority == coo$lastRequested && now == coo$lastResult)) {
                return;
            }
        }
        thread.setPriority(priority);
        if (thread.getName().startsWith("Server")) {
            coo$lastThread = thread;
            coo$lastRequested = priority;
            coo$lastResult = thread.getPriority();
        }
    }
}
