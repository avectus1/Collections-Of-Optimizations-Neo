package com.misanthropy.collections_of_optimizations;

import com.misanthropy.collections_of_optimizations.core.CoOMixinPlugin;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/**
 * The in-game config screen. Sections for mods that are not installed are hidden, so the list only shows
 * what can actually do something in this pack; names and descriptions come from the lang file.
 */
public final class ClientConfigScreen {
    private ClientConfigScreen() {
    }

    public static void register(ModContainer modContainer) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class,
                (container, parent) -> new ConfigurationScreen(container, parent, (context, key, original) -> {
                    if (context.keylist().isEmpty() && !sectionApplies(key)) {
                        return null;
                    }
                    return original;
                }));
    }

    /** False only for a top-level section that belongs to a mod which is not loaded. */
    private static boolean sectionApplies(String section) {
        String modId = CoOMixinPlugin.modIdForGroup(section);
        if (modId == null) {
            int underscore = section.indexOf('_');
            if (underscore > 0) {
                modId = CoOMixinPlugin.modIdForGroup(section.substring(0, underscore));
            }
        }
        if (modId == null) {
            // general, vanilla and leaks apply to every pack; any other section is named after its mod id.
            return isKnownNonMod(section) || ModList.get().isLoaded(section);
        }
        return ModList.get().isLoaded(modId);
    }

    private static boolean isKnownNonMod(String section) {
        return section.equals("general") || section.equals("vanilla") || section.equals("leaks");
    }
}
