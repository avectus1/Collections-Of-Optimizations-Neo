package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.client.gui.screens.Screen;

public final class XaeroWorldMapScreenCheck {
    private XaeroWorldMapScreenCheck() {
    }

    public static boolean isMapScreen(Screen screen) {
        for (Class<?> type = screen == null ? null : screen.getClass(); type != null; type = type.getSuperclass()) {
            if ("xaero.map.gui.GuiMap".equals(type.getName())) {
                return true;
            }
        }
        return false;
    }
}
