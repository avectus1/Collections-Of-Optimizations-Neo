package com.misanthropy.collections_of_optimizations.core;

import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.neoforge.common.NeoForge;

/** Frame counter used to keep Xaero config answers for at most one rendered frame. */
public final class XaeroConfigFrame {
    public static volatile int frame;

    private XaeroConfigFrame() {
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener((RenderFrameEvent.Pre event) -> frame++);
    }
}
