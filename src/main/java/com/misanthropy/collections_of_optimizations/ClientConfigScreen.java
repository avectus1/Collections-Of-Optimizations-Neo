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
                (container, parent) -> new ConfigurationScreen(container, parent));
    }

    private static java.util.Set<String> topLevel;

    /** Names of the top-level sections of CoO's config, read from the spec once. */
    public static boolean isTopLevelSection(String key) {
        java.util.Set<String> names = topLevel;
        if (names == null) {
            names = new java.util.HashSet<>();
            for (var entry : CoOConfig.SPEC.getValues().entrySet()) {
                if (entry.getValue() instanceof com.electronwill.nightconfig.core.UnmodifiableConfig) {
                    names.add(entry.getKey());
                }
            }
            topLevel = names;
        }
        return names.contains(key);
    }

    /** False only for a top-level section that belongs to a mod which is not loaded. */
    public static boolean sectionApplies(String section) {
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
