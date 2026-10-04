package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

public final class ClientTickStamp {

    private static int stamp;

    private ClientTickStamp() {
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(ClientTickStamp::onClientTick);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        stamp++;
    }

    public static int current() {
        return stamp;
    }

    public static int currentOnRenderThread() {
        return Minecraft.getInstance().isSameThread() ? stamp : -1;
    }
}
