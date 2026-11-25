package com.misanthropy.collections_of_optimizations.core;

import com.mojang.blaze3d.pipeline.RenderTarget;

/**
 * Brute Force Culling clears each of its render targets and then binds it again to draw into it.
 * RenderTarget.clear binds the target, clears it and binds framebuffer 0, so every target costs three
 * framebuffer switches where one would do, and with NVIDIA threaded optimisation off (Sodium turns it off)
 * each switch is a driver round trip on the render thread. Only between those two adjacent calls, the
 * clear leaves the target bound and the bind right after it is skipped, so the GL state afterwards is the
 * same as before. Render thread only.
 */
public final class BfcFramebufferBinds {
    /** True only while one of Brute Force Culling's own clear calls is running. */
    public static boolean keepBoundInClear;
    /** The target the last kept-bound clear left bound, until the next bind call looks at it. */
    public static RenderTarget leftBound;

    private BfcFramebufferBinds() {
    }
}
